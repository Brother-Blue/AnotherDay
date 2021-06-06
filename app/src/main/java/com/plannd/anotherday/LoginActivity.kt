package com.plannd.anotherday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "Plannd"
        // Codes
        private const val RC_SIGN_IN = 1000
    }

    // ---- Auth vars ----
    // Google
    private lateinit var _gso: GoogleSignInOptions // Sign in options
    private lateinit var _googleSignInClient: GoogleSignInClient // Sign in client
    // Firebase
    private lateinit var _firebaseAuth: FirebaseAuth // Firebase authentication
    private var _user: FirebaseUser? = null

    // ---- VIEWS ----
    // Buttons
    private lateinit var _btnGoogleSignIn: Button
    private lateinit var _btnEmailSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init firebase auth
        _firebaseAuth = Firebase.auth

        // Before instantiating anything, check if a user login is saved
        _user = _firebaseAuth.currentUser
        if (_user != null) {
            updateUI(_user!!)
        }

        // Setup the sign in options
        _gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        _googleSignInClient = GoogleSignIn.getClient(this, _gso)

        // Init views on activity created
        _btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)
        _btnEmailSignIn = findViewById(R.id.btnEmailSignIn)

        // Set listeners
        _btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Get the account or throw and exception
                    val account = task.getResult(ApiException::class.java)!!
                    // Passed this point, the account is non-null
                    Log.d(TAG, "Firebase auth (Google): " + account.id)
                    // Authenticate the account
                    firebaseAuthGoogleAccount(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed")
                    Toast.makeText(this, "Google sign in failed.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // Update the UI, if the user is not null, move to main activity
    // And finish the login activity
    private fun updateUI(user: FirebaseUser) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    // Google sign in button
    private fun signInWithGoogle() {
        val intent = _googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    // Authenticate Google account with Firebase
    private fun firebaseAuthGoogleAccount(accountToken: String) {
        // Get account credentials using Firebase account token
        val credential = GoogleAuthProvider.getCredential(accountToken, null)
        _firebaseAuth.signInWithCredential(credential)
            // On completion
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Firebase auth credentials success (Google)")
                    _user = _firebaseAuth.currentUser
                    updateUI(_user!!)
                } else {
                    Log.w(TAG, "Firebase auth credentials failure (Google)")
                    Toast.makeText(this, "Google authentication in failed", Toast.LENGTH_LONG).show()
                }
            }
    }
}
package com.plannd.anotherday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

class LoginActivity : AppCompatActivity() {

    // Google sign in vars
    private lateinit var _gso: GoogleSignInOptions // Sign in options
    private lateinit var _googleSignInClient: GoogleSignInClient // Sign in client
    private var _googleAccount: GoogleSignInAccount? = null // Signed in account
    // Signed in account

    // ---- VIEWS ----
    // Buttons
    private lateinit var _btnGoogleSignIn: SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Setup the sign in options
        _gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build sign in client with options
        _googleSignInClient = GoogleSignIn.getClient(this, _gso)

        // Init views on activity created
        _btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)


    }

    override fun onStart() {
        super.onStart()

        // Get last signed in account, null if new user
        _googleAccount = GoogleSignIn.getLastSignedInAccount(this)

        // View settings that can't be explicitly defined in XML
        _btnGoogleSignIn.setSize(SignInButton.SIZE_STANDARD)
    }
}
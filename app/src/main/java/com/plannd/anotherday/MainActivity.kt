package com.plannd.anotherday

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    // Request codes
    companion object {
        const val TAG: String = "Another Day"
        private const val RC_SIGN_IN = 500
    }

    // The logged in user
    private var _user: FirebaseUser? = null

    // Login methods
    private val _providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
        AuthUI.IdpConfig.EmailBuilder().build()
    )

    // Called when activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if a user has a saved login
        _user = FirebaseAuth.getInstance().currentUser
        // If the user is not logged in startup the login activity
        if (_user == null) {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(_providers)
                    .build(),
                RC_SIGN_IN)
        } else {
            // TODO: ("The user is signed in here")
        }
    }

    // Called when starting an activity for a result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                // Get the response from sign-in attempt
                val response = IdpResponse.fromResultIntent(data)
                if (resultCode == Activity.RESULT_OK) {
                    // If sign-in successful, assign the user
                    _user = FirebaseAuth.getInstance().currentUser
                } else {
                    // Log the error and inform the user
                    Log.e(TAG, response?.error?.errorCode.toString())
                    Toast.makeText(this, "Sign-in failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
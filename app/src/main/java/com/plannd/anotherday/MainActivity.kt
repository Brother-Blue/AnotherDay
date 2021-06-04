package com.plannd.anotherday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    // Request codes
    companion object {
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
}
package com.plannd.anotherday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // Authenticator and user
    private lateinit var _firebaseAuth: FirebaseAuth

    // ---- VIEWS ----
    // Buttons
    private lateinit var _btnSignOut: Button

    // Called when activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init Firebase auth
        _firebaseAuth = Firebase.auth

        // Init views
        _btnSignOut = findViewById(R.id.btnSignOut)

        // Set listeners
        _btnSignOut.setOnClickListener {
            signOut()
        }
    }

    // Signed out of account
    private fun signOut() {
        _firebaseAuth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
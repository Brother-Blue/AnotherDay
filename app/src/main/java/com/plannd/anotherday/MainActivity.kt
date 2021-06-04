package com.plannd.anotherday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    // Request codes
    companion object {
        const val TAG: String = "Another Day"
    }

    // The logged in user
    private var _user: FirebaseUser? = null

    // Called when activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the signed in user
        _user = FirebaseAuth.getInstance().currentUser
    }
}
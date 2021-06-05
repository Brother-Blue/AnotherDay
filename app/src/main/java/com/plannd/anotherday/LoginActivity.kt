package com.plannd.anotherday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.SignInButton

class LoginActivity : AppCompatActivity() {

    // ---- VIEWS ----
    // Buttons
    private lateinit var _btnGoogleSignIn: SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init views on activity created
        _btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)
    }

    override fun onStart() {
        super.onStart()

        // View settings that can't be explicitly defined in XML
        _btnGoogleSignIn.setSize(SignInButton.SIZE_STANDARD)
    }
}
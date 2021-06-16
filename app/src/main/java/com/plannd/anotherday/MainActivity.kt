package com.plannd.anotherday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.plannd.anotherday.fragments.MyListsFragment
import com.plannd.anotherday.fragments.MyTasksFragment

class MainActivity : AppCompatActivity() {

    // Authenticator and user
    private lateinit var _firebaseAuth: FirebaseAuth

    // ---- VIEWS ----
    // App bar
    private lateinit var _topAppBar: MaterialToolbar

    // Nav drawer
    private lateinit var _layoutDrawer: DrawerLayout
    private lateinit var _navigationView: NavigationView
    private lateinit var _btnSignOut: Button

    // Containers
    private lateinit var _containerFragment: FragmentContainerView

    // Called when activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init Firebase auth
        _firebaseAuth = Firebase.auth

        // Init views
        _topAppBar = findViewById(R.id.appBarTop)
        _layoutDrawer = findViewById(R.id.layoutDrawer)
        _navigationView = findViewById(R.id.navigationView)
        _btnSignOut = findViewById(R.id.btnSignOut)
        _containerFragment = findViewById(R.id.fragmentContainerView)

        // Set listeners
        _topAppBar.setNavigationOnClickListener {
            _layoutDrawer.openDrawer(GravityCompat.START)
        }

        _btnSignOut.setOnClickListener {
            signOut()
        }

        // Inflate initial fragment
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<MyTasksFragment>(_containerFragment.id)
                setReorderingAllowed(true)
            }
        }

        // Navigation drawer on click
        _navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navMyTasks -> {
                    // If not on My Tasks go to my tasks
                    if (!item.isChecked) {
                        supportFragmentManager.commit {
                            replace<MyTasksFragment>(_containerFragment.id)
                            setReorderingAllowed(true)
                            addToBackStack("MyTasks")
                        }
                    }
                }
                R.id.navMyLists -> {
                    // If not on My Lists open my lists fragment
                    if (!item.isChecked) {
                        supportFragmentManager.commit {
                            replace<MyListsFragment>(_containerFragment.id)
                            setReorderingAllowed(true)
                            addToBackStack("MyLists")
                        }
                    }
                }
                R.id.navMyProjects -> {
                    // If not on My Projects fo to my projects
                    if (!item.isChecked) {

                    }
                }
            }
            // Set current item to checked
            item.isChecked = true
            // Close the nav drawer and ret true
            _layoutDrawer.closeDrawer(_navigationView)
            true
        }
    }

    // Signed out of account
    private fun signOut() {
        _firebaseAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
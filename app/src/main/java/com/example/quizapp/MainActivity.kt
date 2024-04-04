package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import com.google.firebase.auth.FirebaseAuth
import android.net.ConnectivityManager
import androidx.activity.enableEdgeToEdge


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        // Check internet connectivity
        if (!isOnline()) {
            showOfflineScreen()
            return
        }

         //Check if user is already logged in
        if (auth.currentUser != null) {
            // User is already logged in, navigate to DashboardScreen
            goToDashboardScreen()
        }

        // go to signuppage
        val gotoSignUpScreen = findViewById<Button>(R.id.roundedButton)
        gotoSignUpScreen.setOnClickListener {
            val intent = Intent(this, SignupScreen::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun showOfflineScreen() {
        val intent = Intent(this, OffLineScreen::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToDashboardScreen() {
        val intent = Intent(this, DashboardScreen::class.java)
        startActivity(intent)
        finish()

    }


}
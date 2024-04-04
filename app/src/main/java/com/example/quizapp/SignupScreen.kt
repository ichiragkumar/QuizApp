package com.example.quizapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth

import  com.example.quizapp.databinding.ActivitySignupScreenBinding


class SignupScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val binding: ActivitySignupScreenBinding by lazy {
        ActivitySignupScreenBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //create account and call this function
        auth = FirebaseAuth.getInstance()

        // go to signuppage
        val gotoSignUpScreen = findViewById<Button>(R.id.gotoLoginScreen)
        gotoSignUpScreen.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }


        binding.buttonSignUp.setOnClickListener {
            signUpUser()
        }
    }

        private fun signUpUser() {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.editTextEmail.error = "Email is required"
                binding.editTextEmail.requestFocus()
                return
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editTextEmail.error = "Invalid email"
                binding.editTextEmail.requestFocus()
                return
            }

            if (password.isEmpty()) {
                binding.editTextPassword.error = "Password is required"
                binding.editTextPassword.requestFocus()
                return
            }

            if (password.length < 6) {
                binding.editTextPassword.error = "Password must be at least 6 characters"
                binding.editTextPassword.requestFocus()
                return
            }

            // Create user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up success
                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                        // Navigate to DashboardScreen
                        val intent = Intent(this, DashboardScreen::class.java)
                        startActivity(intent)
                        finish() // Optional: Finish current activity to prevent user from going back
                    } else {
                        // Sign up failed
                        Toast.makeText(
                            baseContext, "Sign up failed. ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }



        }
}
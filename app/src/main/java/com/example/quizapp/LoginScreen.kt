package com.example.quizapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        auth = FirebaseAuth.getInstance()

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textViewSignUp = findViewById<TextView>(R.id.textViewSignUp)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (email.isEmpty()) {
                editTextEmail.error = "Email is required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                editTextPassword.error = "Password is required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            // Perform login using Firebase Authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login successful, navigate to DashboardScreen
                        val intent = Intent(this, DashboardScreen::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Login failed
                        Toast.makeText(
                            baseContext, "Login failed. ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        textViewSignUp.setOnClickListener {
            // Navigate to SignUpActivity
            val intent = Intent(this, SignupScreen::class.java)
            startActivity(intent)
        }

    }
}
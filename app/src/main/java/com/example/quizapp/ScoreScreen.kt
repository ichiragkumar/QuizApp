package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class ScoreScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        val emailTextView: TextView = findViewById(R.id.emailTextView)

        // Check if the user is logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is logged in, retrieve and display the email address
            val email = currentUser.email
            emailTextView.text = "$email"
        } else {
            // User is not logged in, display a message
            emailTextView.text = "Email: Not logged in"
        }

        val score = intent.getIntExtra("SCORE", 0)
        Toast.makeText(this, "Your Score: $score", Toast.LENGTH_SHORT).show()


        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        scoreTextView.text = "$score"

        // go to QuizsScreen
        val gotoQuizessScreen = findViewById<Button>(R.id.button)
        gotoQuizessScreen.setOnClickListener {
            val intent = Intent(this, QuizesScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}
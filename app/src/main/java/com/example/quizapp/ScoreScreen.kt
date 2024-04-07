package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val score = intent.getIntExtra("SCORE", 0)

        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        scoreTextView.text = "Your Score: $score"

        // go to QuizsScreen
        val gotoQuizessScreen = findViewById<Button>(R.id.button)
        gotoQuizessScreen.setOnClickListener {
            val intent = Intent(this, QuizesScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}
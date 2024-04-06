package com.example.quizapp


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userButton = findViewById<ImageButton>(R.id.userButton)
        val imageButton = findViewById<ImageButton>(R.id.imageButton)

        userButton.setOnClickListener {
            // Start QuizScreen
            startActivity(Intent(this, EditProfileScreen::class.java))
        }

        imageButton.setOnClickListener {
            // Start ProfileScreen
            startActivity(Intent(this,QuizesScreen ::class.java))
        }
    }
}
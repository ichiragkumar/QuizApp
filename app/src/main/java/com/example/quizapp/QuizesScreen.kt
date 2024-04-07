package com.example.quizapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class QuizesScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quizes_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // go to Quiz1
        val quiz1 = findViewById<ImageButton>(R.id.imageButton2)
        quiz1.setOnClickListener {
            val intent = Intent(this, Quiz1Screen::class.java)
            startActivity(intent)
            finish()
        }

        // go to Quiz2
        val quiz2 = findViewById<ImageButton>(R.id.imageButton3)
        quiz2.setOnClickListener {
            val intent = Intent(this, Quiz2Screen::class.java)
            startActivity(intent)
            finish()
        }

        // go to Quiz3
        val quiz3 = findViewById<ImageButton>(R.id.imageButton5)
        quiz3.setOnClickListener {
            val intent = Intent(this, Quiz3Screen::class.java)
            startActivity(intent)
            finish()
        }



    }
}
package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Quiz2Screen : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var optionRadioGroup: RadioGroup
    private lateinit var option1RadioButton: RadioButton
    private lateinit var option2RadioButton: RadioButton
    private lateinit var option3RadioButton: RadioButton
    private lateinit var option4RadioButton: RadioButton
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var quitButton: Button


    private var currentQuestionIndex = 0
    private var score = 0

    private val questions = listOf(
        Question(
            "1.  Which country is known as the 'Land of the Rising Sun'?",
            listOf("China", "Japan", "South Korea", "Vietnam"),
            1
        ),
        Question(
            "2.  What is the capital of Australia?",
            listOf("Sydney", "Melbourne", "Canberra", "Brisbane"),
            2
        ),
        Question(
            "3.  Which country is the world's largest producer of oil?",
            listOf("United States", "Saudi Arabia", "Russia", "Iran"),
            1
        ),
        Question(
            "4.  What is the longest river \n in Africa?",
            listOf("Nile", "Congo", "Niger", "Zambezi"),
            0
        ),
        Question(
            "5.  What is the official language of Brazil?",
            listOf("Spanish", "Portuguese", "English", "French"),
            1
        ),
        Question(
            "6.  Which city is known as the \n 'City of Love'?",
            listOf("Paris", "Rome", "Venice", "Florence"),
            0
        ),
        Question(
            "7.  What is the smallest country in the \n world by land area?",
            listOf("Monaco", "Vatican City", "Nauru", "Maldives"),
            1
        ),
        Question(
            "8.  Which continent is the largest by land area?",
            listOf("Asia", "Africa", "North America", "Europe"),
            0
        ),
        Question(
            "9.  What is the capital of South Africa?",
            listOf("Pretoria", "Johannesburg", "Cape Town", "Bloemfontein"),
            2
        ),
        Question(
            "10. Which country is known as the \n 'Emerald Isle'?",
            listOf("Scotland", "Ireland", "Greenland", "Iceland"),
            1
        ),


        // Add more questions here
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz2_screen)



        questionTextView = findViewById(R.id.questionTextView)
        optionRadioGroup = findViewById(R.id.optionRadioGroup)
        option1RadioButton = findViewById(R.id.option1RadioButton)
        option2RadioButton = findViewById(R.id.option2RadioButton)
        option3RadioButton = findViewById(R.id.option3RadioButton)
        option4RadioButton = findViewById(R.id.option4RadioButton)
        nextButton = findViewById(R.id.nextButton)
        previousButton = findViewById(R.id.previousButton)
        quitButton = findViewById(R.id.quitButton)


        updateQuestion()
        nextButton.setOnClickListener {
            checkAnswer() // Check the answer before moving to the next question
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                updateQuestion()
                if (currentQuestionIndex == questions.size - 1) {
                    nextButton.text = "Submit"
                }
            } else {
                navigateToScoreScreen()
            }
        }


        previousButton.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                updateQuestion()
            }
        }

        quitButton.setOnClickListener {
            onBackPressed()
        }
    }
    private fun updateQuestion() {
        val currentQuestion = questions[currentQuestionIndex]
        questionTextView.text = currentQuestion.question
        option1RadioButton.text = currentQuestion.options[0]
        option2RadioButton.text = currentQuestion.options[1]
        option3RadioButton.text = currentQuestion.options[2]
        option4RadioButton.text = currentQuestion.options[3]
        optionRadioGroup.clearCheck()
    }

    private fun checkAnswer() {
        val checkedRadioButtonId = optionRadioGroup.checkedRadioButtonId
        if (checkedRadioButtonId != -1) {
            val selectedOptionIndex = optionRadioGroup.indexOfChild(findViewById(checkedRadioButtonId))
            if (selectedOptionIndex == questions[currentQuestionIndex].correctAnswerIndex) {
                score++
            }
        }
    }


    private fun navigateToScoreScreen() {
        val intent = Intent(this, ScoreScreen::class.java)
        intent.putExtra("SCORE", score)
        startActivity(intent)
        finish()
    }



    data class Question(val question: String, val options: List<String>, val correctAnswerIndex: Int)
}

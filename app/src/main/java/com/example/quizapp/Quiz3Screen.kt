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

class Quiz3Screen : AppCompatActivity() {

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
            "1.  What is the value of π (pi)\n correct to two decimal places?",
            listOf("3.14", "3.16", "3.12", "3.18"),
            0
        ),
        Question(
            "2.  What is the square root of 144?",
            listOf("10", "12", "14", "16"),
            1
        ),
        Question(
            "3.  What is the result of 5 \n  multiplied by 8?",
            listOf("30", "35", "40", "45"),
            2
        ),
        Question(
            "4.  What is the value of 'x' \n in the equation 2x + 5 = 15?",
            listOf("5", "7", "8", "10"),
            0
        ),
        Question(
            "5.  What is the area of a rectangle \n  with length 6 units and width 4 units?",
            listOf("20", "24", "28", "30"),
            1
        ),
        Question(
            "6.  If a car travels at a speed \n of 60 miles per hour, how far \n will it travel in 3 hours?",
            listOf("120 miles", "150 miles", "180 miles", "200 miles"),
            2
        ),
        Question(
            "7.  What is the value of 7\n  factorial (7!)?",
            listOf("42", "49", "56", "5040"),
            3
        ),
        Question(
            "8.  What is the sum of the first \n 10 prime numbers?",
            listOf("125", "200", "270", "129"),
            2
        ),
        Question(
            "9.  How many degrees are there in \n a right angle?",
            listOf("90", "100", "120", "180"),
            0
        ),
        Question(
            "10. What is the value of 'y' \n in the equation 2y - 3 = 7?",
            listOf("2", "3", "4", "5"),
            2
        ),


    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz3_screen)


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



package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Quiz1Screen : AppCompatActivity() {

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
        Question("1.  What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), 2),
        Question("2.  What is the largest planet in our solar system?", listOf("Earth", "Mars", "Jupiter", "Venus"), 2),
        Question("3.  What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), 2),
        Question("4.  What is the largest planet in our solar system?", listOf("Earth", "Mars", "Jupiter", "Venus"), 2),
        Question("5.  What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), 2),
        Question("6.  What is the largest planet in our solar system?", listOf("Earth", "Mars", "Jupiter", "Venus"), 2),
        Question("7.  What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), 2),
        Question("8.  What is the largest planet in our solar system?", listOf("Earth", "Mars", "Jupiter", "Venus"), 2),
        Question("9.  What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), 2),
        Question("10. What is the largest planet in our solar system?", listOf("Earth", "Mars", "Jupiter", "Venus"), 2),

        // Add more questions here
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz1_screen)

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
//        nextButton.setOnClickListener {
//            checkAnswer()
//            if (currentQuestionIndex < questions.size - 1) {
//                currentQuestionIndex++
//                updateQuestion()
//                if (currentQuestionIndex == questions.size - 1) {
//                    nextButton.text = "Submit"
//                }
//            } else {
//                checkAnswer()
//                navigateToScoreScreen()
//            }
//        }

//        --------
//        nextButton.setOnClickListener {
//            checkAnswer() // Check the answer before moving to the next question
//            if (currentQuestionIndex < questions.size - 1) {
//                currentQuestionIndex++
//                updateQuestion()
//                if (currentQuestionIndex == questions.size - 1) {
//                    nextButton.text = "Submit"
//                }
//            } else {
//                navigateToScoreScreen()
//            }
//        }

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

//    private fun checkAnswer() {
//        val checkedRadioButtonId = optionRadioGroup.checkedRadioButtonId
//        if (checkedRadioButtonId != -1) {
//            val selectedOptionIndex = optionRadioGroup.indexOfChild(findViewById(checkedRadioButtonId))
//            if (selectedOptionIndex == questions[currentQuestionIndex].correctAnswerIndex) {
//                score++
//            }
//        }
//    }

    private fun navigateToScoreScreen() {
        val intent = Intent(this, ScoreScreen::class.java)
        intent.putExtra("SCORE", score)
        startActivity(intent)
        finish()
    }



    data class Question(val question: String, val options: List<String>, val correctAnswerIndex: Int)
}

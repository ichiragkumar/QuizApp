package com.example.quizapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

class Quiz4Screen : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var optionRadioGroup: RadioGroup
    private lateinit var option1RadioButton: RadioButton
    private lateinit var option2RadioButton: RadioButton
    private lateinit var option3RadioButton: RadioButton
    private lateinit var option4RadioButton: RadioButton
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var quitButton: Button

    private var previousCheckedRadioButton: RadioButton? = null


    private var currentQuestionIndex = 0
    private var score = 0

    private val questions = listOf(
        Quiz3Screen.Question(
            "1. What number logically follows series? \n7 9 5 11 4 15 12 7 13 8 11 ??",
            listOf("8", "10", "11", "13"),
            1
        ),
        Quiz3Screen.Question(
            "2. What is the next number in the sequence? \n2 5 7 4 7 5 3 6 ??",
            listOf("4", "6", "8", "10"),
            1
        ),
        Quiz3Screen.Question(
            "3. What comes next in the\n sequence? 2, 5, 8, 11, ??",
            listOf("8", "12", "14", "16"),
            3
        ),
        Quiz3Screen.Question(
            "4. What is the next number\n in the sequence? \n121, 144, 169, 196, ??",
            listOf("225", "230", "275", "221"),
            1
        ),
        Quiz3Screen.Question(
            "5. What number should come next? \n4, 6, 9, 6, 14, 6, ??",
            listOf("14", "9", "16", "19"),
            4
        ),
        Quiz3Screen.Question(
            "6. What is the next number in the sequence? \n2, 3, 5, 9, 17, 33, 65, ??",
            listOf("104", "129", "97", "135"),
            2
        ),
        Quiz3Screen.Question(
            "7. What comes next\n in the sequence? \n1, 3, 12, 52, 265, ??",
            listOf("1188", "1390", "1489", "1596"),
            4
        ),
        Quiz3Screen.Question(
            "8. What is the next number\n in the sequence? \n2, 8, 26, 62, 122, 212, ??",
            listOf("338", "339", "340", "341"),
            1
        ),
        Quiz3Screen.Question(
            "9. What is the next prime number after 29?",
            listOf("30", "31", "33", "34"),
            2
        ),
        Quiz3Screen.Question(
            "10. What number comes next? \n2, 3, 6, 11, 18, 27, ??",
            listOf("21", "28", "38", "41"),
            3
        )



    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz4_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


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
            val checkedRadioButtonId = optionRadioGroup.checkedRadioButtonId
            if (checkedRadioButtonId != -1) {
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
            } else {
                // Show warning to the user
                Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show()
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




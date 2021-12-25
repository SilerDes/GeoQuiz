package com.siler.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var correctAnswerCount = 0
    private var inCorrectAnswerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)
        prevButton = findViewById(R.id.prevButton)
        questionTextView = findViewById(R.id.questionTextView)

        trueButton.setOnClickListener {
            checkAnswer(true)
            checkResult()
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            checkResult()
        }

        nextButton.setOnClickListener {
            handleQuestionTransition()
        }

        prevButton.setOnClickListener {
            handleQuestionTransition(-1)
        }

        questionTextView.setOnClickListener {
            handleQuestionTransition()
        }

        updateQuestion()

        Log.d(TAG, "onCreate(Bundle?)")
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }

    private fun handleQuestionTransition(k: Int = 0) {

        if(k == 0) {
            currentIndex = (currentIndex + 1) % questionBank.size
        } else {
            currentIndex--
            if(currentIndex < 0) currentIndex = questionBank.size - 1
        }

        checkEnableButton()

        updateQuestion()
    }

    private fun toastTop(resId: Int) {
        val toast = Toast.makeText(this, resId, Toast.LENGTH_SHORT)
        toast.apply {
            setGravity(Gravity.TOP,0,0)
            show()
        }
    }

    private fun toast(@StringRes resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if(userAnswer == correctAnswer) {
            correctAnswerCount++
            R.string.correct_toast
        } else {
            inCorrectAnswerCount++
            R.string.incorrect_toast
        }

        toast(messageResId)
        questionBank[currentIndex].isLocked = true
        checkEnableButton()
    }

    private fun checkEnableButton() {
        trueButton.isEnabled = !questionBank[currentIndex].isLocked
        falseButton.isEnabled = !questionBank[currentIndex].isLocked
    }

    private fun checkResult() {
        if(correctAnswerCount + inCorrectAnswerCount == questionBank.size) {
            val message = "You done GeoQuiz with ${
                ((correctAnswerCount.toDouble() / questionBank.size) * 100).toInt()
            }% correct answers!"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}
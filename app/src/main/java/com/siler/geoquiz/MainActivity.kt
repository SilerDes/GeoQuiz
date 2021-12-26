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
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
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
        quizViewModel.move(k)
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
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if(userAnswer == correctAnswer) {
            quizViewModel.correctAnswerCount++
            R.string.correct_toast
        } else {
            quizViewModel.inCorrectAnswerCount++
            R.string.incorrect_toast
        }

        toast(messageResId)
        quizViewModel.setQuestionProperty(true)
        checkEnableButton()
    }

    private fun checkEnableButton() {
        trueButton.isEnabled = quizViewModel.isLocked
        falseButton.isEnabled = quizViewModel.isLocked
    }

    private fun checkResult() {
        val size = quizViewModel.bankSize
        if(quizViewModel.correctAnswerCount + quizViewModel.inCorrectAnswerCount == size) {
            val message = "You done GeoQuiz with ${
                ((quizViewModel.correctAnswerCount.toDouble() / size) * 100).toInt()
            }% correct answers!"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val TAG = "MainActivity"
        private const val KEY_INDEX = "index"
    }
}
package com.siler.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class CheatActivity : AppCompatActivity() {

    private var answer = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(this).get(CheatViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answer = intent.getBooleanExtra(KEY_ANSWER, false)
        answerTextView = findViewById(R.id.answerTextView)
        showAnswerButton = findViewById(R.id.showAnswerButton)

        showAnswerButton.setOnClickListener {
            updateAnswerTextView()
            setAnswerShownResult()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        updateAnswerTextView()
        setResult(cheatViewModel.resultCode, cheatViewModel.data)
    }

    private fun updateAnswerTextView() {
        answerTextView.text = answer.toString().uppercase()
    }

    private fun setAnswerShownResult() {
        cheatViewModel.data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, true)
        }
        cheatViewModel.resultCode = Activity.RESULT_OK
        setResult(cheatViewModel.resultCode, cheatViewModel.data)
    }

    companion object {
        private const val KEY_ANSWER = "answer"
        const val EXTRA_ANSWER_SHOWN = "answer_shown"

        fun newIntent(packageContext: Context, answer: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(KEY_ANSWER, answer)
            }
        }
    }
}
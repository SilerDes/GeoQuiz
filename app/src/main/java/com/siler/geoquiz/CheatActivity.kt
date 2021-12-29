package com.siler.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class CheatActivity : AppCompatActivity() {

    private var answer = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var buildVersionTextView: TextView
    private lateinit var hintCountTextView: TextView

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(this).get(CheatViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        if(savedInstanceState != null) {
            cheatViewModel.hintCount = savedInstanceState.getInt(KEY_HINT_COUNT)
        } else {
            cheatViewModel.hintCount = intent.getIntExtra(KEY_HINT_COUNT, 0)
        }

        answer = intent.getBooleanExtra(KEY_ANSWER, false)
        answerTextView = findViewById(R.id.answerTextView)
        showAnswerButton = findViewById(R.id.showAnswerButton)
        buildVersionTextView = findViewById(R.id.buildVersionTextView)
        hintCountTextView = findViewById(R.id.hintCountTextView)


        showAnswerButton.setOnClickListener {
            updateAnswerTextView()
            if(!cheatViewModel.isAnswerShown) {
                cheatViewModel.isAnswerShown = true
                cheatViewModel.hintCount = cheatViewModel.hintCount - 1
            }
            updateHintCount()
            setAnswerShownResult()
            checkEnableButton()
        }

        updateHintCount()
        buildVersionTextView.text = "API Level ${Build.VERSION.SDK_INT}"

        checkEnableButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_HINT_COUNT, cheatViewModel.hintCount)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        updateAnswerTextView()
        setResult(cheatViewModel.resultCode, cheatViewModel.data)
    }

    private fun checkEnableButton() {
        showAnswerButton.isEnabled = cheatViewModel.hintCount > 0
    }

    private fun updateAnswerTextView() {
        answerTextView.text = answer.toString().uppercase()
    }

    private fun updateHintCount() {
        hintCountTextView.text = cheatViewModel.hintCount.toString()
    }

    private fun setAnswerShownResult() {
        cheatViewModel.data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, true)
            putExtra(KEY_HINT_COUNT, cheatViewModel.hintCount)
        }
        cheatViewModel.resultCode = Activity.RESULT_OK
        setResult(cheatViewModel.resultCode, cheatViewModel.data)
    }

    companion object {
        private const val KEY_ANSWER = "answer"
        const val KEY_HINT_COUNT = "hint_count"
        const val EXTRA_ANSWER_SHOWN = "answer_shown"

        fun newIntent(packageContext: Context, answer: Boolean, hintCount: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(KEY_ANSWER, answer)
                putExtra(KEY_HINT_COUNT, hintCount)
            }
        }
    }
}
package com.siler.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    var currentIndex = 0

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val isLocked: Boolean
        get() = !questionBank[currentIndex].isLocked
    val bankSize: Int
        get() = questionBank.size

    var correctAnswerCount = 0
    var inCorrectAnswerCount = 0

    fun move(k: Int = 0) {
        if(k == 0) {
            currentIndex = (currentIndex + 1) % questionBank.size
        } else {
            currentIndex--
            if(currentIndex < 0) currentIndex = questionBank.size - 1
        }
    }

    fun setQuestionProperty(property: Boolean) {
        questionBank[currentIndex].isLocked = property
    }
}
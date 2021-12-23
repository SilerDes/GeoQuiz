package com.siler.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)

        trueButton.setOnClickListener {
            toastBottom(R.string.correct_toast)
        }

        falseButton.setOnClickListener {
            toastBottom(R.string.incorrect_toast)
        }
    }

    private fun toastTop(resId: Int) {
        val toast = Toast.makeText(this, resId, Toast.LENGTH_SHORT)
        toast.apply {
            setGravity(Gravity.TOP,0,0)
            show()
        }
    }

    private fun toastBottom(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}
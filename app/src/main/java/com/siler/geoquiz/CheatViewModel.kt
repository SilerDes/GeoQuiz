package com.siler.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel

class CheatViewModel : ViewModel() {
    var data: Intent? = null
    var resultCode = Activity.RESULT_CANCELED
}
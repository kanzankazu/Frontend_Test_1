package com.danamon.core.ext

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T : AppCompatActivity> Context?.makeIntent() =
    Intent(this, T::class.java)

fun Context.delayFun(delayMillis: Long = 500, r: () -> Unit): Boolean {
    return Handler(Looper.getMainLooper()).postDelayed(r, delayMillis)
}

fun Context.simpleToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
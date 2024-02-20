package com.danamon.core.ext

import android.os.Build
import android.util.Log
import android.widget.TextView
import java.util.Base64

fun debugMessage(string: String) {
    if (isDebug()) Log.d("Lihat DebugMessage", string)
}

fun debugMessageWarning(string: String) {
    if (isDebug()) Log.w("Lihat debugMessageWarning", string)
}

fun debugMessageError(string: String) {
    if (isDebug()) Log.e("Lihat DebugMessageError", string)
}

fun TextView.string(defaultValue: String = ""): String = if (text.toString().trim().isNotEmpty()) text.toString().trim { it <= ' ' } else defaultValue

fun String.encodeString() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    Base64.getEncoder().encodeToString(this.toByteArray())
} else {
    val encodedBytes = android.util.Base64.encode(this.toByteArray(), android.util.Base64.NO_WRAP)
    String(encodedBytes)
}

fun String.decodedString() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val decodedBytes = Base64.getDecoder().decode(this)
    String(decodedBytes)
} else {
    val decodedBytes = android.util.Base64.decode(this.toByteArray(), android.util.Base64.NO_WRAP)
    String(decodedBytes)
}

fun String.toIntOrDefault(defaultValue: Int = 0) = toIntOrNull() ?: defaultValue

fun String.toBooleanOrFalse(defaultValue: Boolean = false): Boolean = toBooleanStrictOrNull() ?: false
package com.danamon.core.ext

import android.content.Intent

fun Intent.addClearTaskNewTask() = addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
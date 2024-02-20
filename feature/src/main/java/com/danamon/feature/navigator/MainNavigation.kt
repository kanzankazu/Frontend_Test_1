package com.danamon.feature.navigator

import android.content.Context
import android.content.Intent

interface MainNavigation {
    fun createIntentMainActivity(context: Context): Intent
}
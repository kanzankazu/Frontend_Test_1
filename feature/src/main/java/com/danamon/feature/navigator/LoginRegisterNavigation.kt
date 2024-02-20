package com.danamon.feature.navigator

import android.content.Context
import android.content.Intent

interface LoginRegisterNavigation {
    fun createIntentLoginRegisterActivity(context: Context): Intent
}
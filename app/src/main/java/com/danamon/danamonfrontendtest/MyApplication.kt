package com.danamon.danamonfrontendtest

import android.app.Application
import com.danamon.danamonfrontendtest.di.AppScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    @AppScope
    lateinit var appCoroutineScope: CoroutineScope
}

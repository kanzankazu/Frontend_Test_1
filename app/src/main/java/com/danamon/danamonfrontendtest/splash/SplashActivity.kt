package com.danamon.danamonfrontendtest.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.danamon.core.base.BaseActivityBindingView
import com.danamon.core.ext.addClearTaskNewTask
import com.danamon.core.ext.use
import com.danamon.core.util.typing
import com.danamon.danamonfrontendtest.databinding.ActivitySplashBinding
import com.danamon.feature.navigator.LoginRegisterNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivityBindingView<ActivitySplashBinding>() {

    @Inject
    lateinit var loginRegisterNavigation: LoginRegisterNavigation

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun setContent() = binding.use {
        tvActivitySplash.typing(getString(com.danamon.core.R.string.label_hello_tester), delayMillis = 250) {
            val intent = loginRegisterNavigation.createIntentLoginRegisterActivity(this@SplashActivity).addClearTaskNewTask()
            startActivity(intent)
        }
    }
}
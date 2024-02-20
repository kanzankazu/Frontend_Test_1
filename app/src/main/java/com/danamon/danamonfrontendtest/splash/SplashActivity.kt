package com.danamon.danamonfrontendtest.splash

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.danamon.core.base.BaseActivityBindingView
import com.danamon.core.base.baseresponse.handleBaseResponse
import com.danamon.core.ext.addClearTaskNewTask
import com.danamon.core.ext.simpleToast
import com.danamon.core.ext.use
import com.danamon.core.ext.vmLoadDataRe
import com.danamon.core.util.typing
import com.danamon.danamonfrontendtest.databinding.ActivitySplashBinding
import com.danamon.feature.navigator.LoginRegisterNavigation
import com.danamon.feature.navigator.MainNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivityBindingView<ActivitySplashBinding>() {

    @Inject
    lateinit var loginRegisterNavigation: LoginRegisterNavigation

    @Inject
    lateinit var mainNavigation: MainNavigation

    private val viewModel by viewModels<SplashViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun setContent() = binding.use {
        tvActivitySplash.typing(getString(com.danamon.core.R.string.label_hello_tester), delayMillis = 100) {
            viewModel.checkLogin()
        }
    }

    override fun setSubscribeToLiveData() {
        vmLoadDataRe(viewModel.checkLogin) {
            it.handleBaseResponse(
                onError = { s -> simpleToast(s) },
                onSuccess = { b -> if (b) gotoMain() else gotoLogin() }
            )
        }
    }

    private fun gotoMain() {
        val intent = mainNavigation.createIntentMainActivity(this).addClearTaskNewTask()
        startActivity(intent)
    }

    private fun gotoLogin() {
        val intent = loginRegisterNavigation.createIntentLoginRegisterActivity(this@SplashActivity).addClearTaskNewTask()
        startActivity(intent)
    }
}
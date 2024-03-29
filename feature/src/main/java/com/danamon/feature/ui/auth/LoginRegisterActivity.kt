package com.danamon.feature.ui.auth

import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.inSpans
import androidx.core.text.scale
import androidx.core.widget.doOnTextChanged
import com.danamon.core.Constant.minCharPassword
import com.danamon.core.Constant.minCharUsername
import com.danamon.core.base.BaseActivityBindingView
import com.danamon.core.base.baseresponse.handleBaseResponse
import com.danamon.core.ext.PermissionEnumArray
import com.danamon.core.ext.addClearTaskNewTask
import com.danamon.core.ext.checkPermissions
import com.danamon.core.ext.get
import com.danamon.core.ext.getRadioGroupIndex
import com.danamon.core.ext.isEmailValid
import com.danamon.core.ext.resultMultiplePermissions
import com.danamon.core.ext.set
import com.danamon.core.ext.simpleToast
import com.danamon.core.ext.use
import com.danamon.core.ext.visibleView
import com.danamon.core.ext.vmLoadDataRe
import com.danamon.core.util.SpannableClick
import com.danamon.feature.R
import com.danamon.feature.databinding.ActivityLoginRegisterBinding
import com.danamon.feature.navigator.MainNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginRegisterActivity : BaseActivityBindingView<ActivityLoginRegisterBinding>() {

    private lateinit var resultMultiplePermissionss: ActivityResultLauncher<Array<String>>

    @Inject
    lateinit var mainNavigation: MainNavigation

    private val viewModel by viewModels<LogRegViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivityLoginRegisterBinding
        get() = ActivityLoginRegisterBinding::inflate

    override fun setActivityResult() {
        resultMultiplePermissionss = resultMultiplePermissions()
    }

    override fun setContent() {
        checkPermissions(PermissionEnumArray.POST_NOTIFICATIONS, resultMultiplePermissionss)
    }

    override fun setListener() = binding.use {
        tilActivityLoginRegisterUsername.helperText = "minimum $minCharUsername characters"
        etActivityLoginRegisterUsername.doOnTextChanged { text, _, _, _ ->
            viewModel.username.set(
                if (text.toString().length >= minCharUsername) text.toString()
                else ""
            )
        }
        etActivityLoginRegisterEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.email.set(
                if (text.toString().isEmailValid()) text.toString()
                else ""
            )
        }
        tilActivityLoginRegisterPassword.helperText = "minimum $minCharPassword characters"
        etActivityLoginRegisterPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.password.set(
                if (text.toString().length >= minCharPassword) text.toString()
                else ""
            )
        }
        rgActivityLoginRegisterRole.setOnCheckedChangeListener { group, _ -> group.getRadioGroupIndex { viewModel.role.set(it) } }
        tvActivityLoginRegisterButtonTitle.setOnClickListener {
            if (viewModel.isLogin.get()) viewModel.loginUser()
            else viewModel.registerUser()
        }
    }

    override fun setSubscribeToLiveData() {
        vmLoadDataRe(viewModel.isLogin) { handleLoginRegisterState(it) }
        vmLoadDataRe(viewModel.validationButton()) { handleLoginRegisterButton(it) }
        vmLoadDataRe(viewModel.loginUser) {
            it.handleBaseResponse(
                onError = { s -> simpleToast(s) },
                onSuccess = { gotoMain() }
            )
        }
        vmLoadDataRe(viewModel.registerUser) {
            it.handleBaseResponse(
                onError = { s -> simpleToast(s) },
                onSuccess = { s ->
                    simpleToast(s)
                    viewModel.isLogin.set(true)
                }
            )
        }
    }

    private fun handleLoginRegisterState(isLogin: Boolean) = binding.use {
        tvActivityLoginRegisterTitle.text = if (isLogin) "Please Login" else "Please Register"
        tvActivityLoginRegisterButtonTitle.text = if (isLogin) getString(R.string.label_login) else getString(R.string.label_register)
        tvActivityLoginRegisterChange.text = buildSpannedString {
            append(if (isLogin) "don't have an account yet? " else "already have an account? ")
            bold {
                scale(1.1f) {
                    color(ContextCompat.getColor(this@LoginRegisterActivity, R.color.colorPrimary)) {
                        inSpans(SpannableClick(tvActivityLoginRegisterChange, onClick = {
                            viewModel.isLogin.set(!viewModel.isLogin.get())
                        })) {
                            append(if (isLogin) "Come on, register" else "Come on, login")
                        }
                    }
                }
            }
        }
        tilActivityLoginRegisterUsername.visibleView(!isLogin)
        rgActivityLoginRegisterRole.visibleView(!isLogin)
    }

    private fun handleLoginRegisterButton(b: Boolean) = binding.use {
        tvActivityLoginRegisterButtonTitle.isEnabled = b
    }

    private fun gotoMain() {
        val intent = mainNavigation.createIntentMainActivity(this).addClearTaskNewTask()
        startActivity(intent)
    }
}
package com.danamon.feature.module.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.danamon.core.Constant.minCharPassword
import com.danamon.core.Constant.minCharUsername
import com.danamon.core.base.BaseBottomSheetFragment
import com.danamon.core.base.baseresponse.handleBaseResponse
import com.danamon.core.ext.getRadioGroupIndex
import com.danamon.core.ext.isEmailValid
import com.danamon.core.ext.set
import com.danamon.core.ext.setRadioGroupIndex
import com.danamon.core.ext.simpleToast
import com.danamon.core.ext.use
import com.danamon.core.ext.vmLoadDataRe
import com.danamon.feature.databinding.DialogEditUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserBottomSheetDialog(private val userId: Long) : BaseBottomSheetFragment() {
    private lateinit var binding: DialogEditUserBinding

    var onSuccessEdit: () -> Unit = {}

    private val viewModel by viewModels<RemoveEditUserViewModel>()

    override fun setLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogEditUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() = binding.use {
        viewModel.userId.set(userId)
        viewModel.getUserData()
        setListener()
        subscribeLiveData()
    }

    private fun setListener() = binding.use {
        tilDialogEditUserUsername.helperText = "minimum $minCharUsername characters"
        etDialogEditUserUsername.doOnTextChanged { text, _, _, _ ->
            viewModel.username.set(
                if (text.toString().length >= minCharUsername) text.toString()
                else ""
            )
        }
        etDialogEditUserEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.email.set(
                if (text.toString().isEmailValid()) text.toString()
                else ""
            )
        }
        tilDialogEditUserPassword.helperText = "minimum $minCharPassword characters"
        etDialogEditUserPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.password.set(
                if (text.toString().length >= minCharPassword) text.toString()
                else ""
            )
        }
        rgDialogEditUserRole.setOnCheckedChangeListener { group, _ -> group.getRadioGroupIndex { viewModel.role.set(it) } }
        tvDialogEditUserButtonTitle.setOnClickListener { viewModel.editUser() }
    }

    private fun subscribeLiveData() = binding.use {
        vmLoadDataRe(viewModel.validationButtonEdit()) { tvDialogEditUserButtonTitle.isEnabled = it }
        vmLoadDataRe(viewModel.getUserData) {
            it.handleBaseResponse(
                onError = { requireContext().simpleToast(it) },
                onSuccess = { user ->
                    viewModel.userId.set(user.userId)
                    viewModel.password.set(user.password)
                    etDialogEditUserUsername.setText(user.username)
                    etDialogEditUserEmail.setText(user.email)
                    etDialogEditUserPassword.setText(user.password)
                    rgDialogEditUserRole.setRadioGroupIndex(user.role, true)
                }
            )
        }
        vmLoadDataRe(viewModel.editUser) {
            it.handleBaseResponse(
                onError = { requireContext().simpleToast(it) },
                onSuccess = {
                    requireContext().simpleToast(it)
                    onSuccessEdit.invoke()
                    dismiss()
                }
            )
        }
    }
}
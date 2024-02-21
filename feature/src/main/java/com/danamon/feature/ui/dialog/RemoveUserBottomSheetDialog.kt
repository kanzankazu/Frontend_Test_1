package com.danamon.feature.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.danamon.core.base.BaseBottomSheetFragment
import com.danamon.core.base.baseresponse.handleBaseResponse
import com.danamon.core.ext.set
import com.danamon.core.ext.simpleToast
import com.danamon.core.ext.use
import com.danamon.core.ext.vmLoadDataRe
import com.danamon.feature.R
import com.danamon.feature.databinding.DialogRemoveUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemoveUserBottomSheetDialog(private val userId: Long) : BaseBottomSheetFragment() {

    var onSuccessRemove: () -> Unit = {}

    private val viewModel by viewModels<RemoveEditUserViewModel>()

    private lateinit var binding: DialogRemoveUserBinding

    override fun setLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogRemoveUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() = binding.use {
        viewModel.userId.set(userId)
        etDialogRemoveUserPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.password.set(text.toString().ifEmpty { "" })
        }
        tvDialogRemoveUserButtonTitle.setOnClickListener {
            viewModel.removeUser()
        }

        subscribeViewModel()
        viewModel.getUserData()
    }

    private fun subscribeViewModel() = binding.use {
        vmLoadDataRe(viewModel.validationButtonRemove()) { tvDialogRemoveUserButtonTitle.isEnabled = it }
        vmLoadDataRe(viewModel.getUserData) {
            it.handleBaseResponse(
                onError = {},
                onSuccess = {
                    tvDialogRemoveUser.text = requireContext().getString(
                        R.string.message_are_you_sure_you_want_to_delete_this_account_if_so_please_enter_your_password,
                        "${it.username}(${it.email})"
                    )
                }
            )
        }
        vmLoadDataRe(viewModel.removeUser) {
            it.handleBaseResponse(
                onError = { s -> requireContext().simpleToast(s) },
                onSuccess = { s ->
                    requireContext().simpleToast(s)
                    onSuccessRemove.invoke()
                    dismiss()
                }
            )
        }
    }
}
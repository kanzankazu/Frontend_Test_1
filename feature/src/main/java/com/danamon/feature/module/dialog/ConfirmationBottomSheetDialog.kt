package com.danamon.feature.module.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.danamon.core.base.BaseBottomSheetFragment
import com.danamon.core.ext.gone
import com.danamon.core.ext.loadImage
import com.danamon.feature.databinding.DialogConfirmationBinding

class ConfirmationBottomSheetDialog(
    val icon: Int,
    val title: String,
    val desc: String = "",
    val negativeText: String = "Tidak",
    val positiveText: String = "Ya",
    val isButtonPositiveGone: Boolean = false,
    val isButtonNegativeGone: Boolean = false,
) : BaseBottomSheetFragment() {

    private lateinit var binding: DialogConfirmationBinding

    private var onPositiveButtonClickListener: ((ConfirmationBottomSheetDialog) -> Unit)? = null
    private var onNegativeButtonClickListener: ((ConfirmationBottomSheetDialog) -> Unit)? = null

    override fun setLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
        binding.imageConfirmation.loadImage(icon)
        binding.textConfirmationTitle.text = title
        if (desc.isNotEmpty()) binding.textConfirmationDesc.text = desc
        else binding.textConfirmationDesc.gone()
        if (negativeText.isNotEmpty()) binding.buttonNegative.text = negativeText
        if (positiveText.isNotEmpty()) binding.buttonPositive.text = positiveText

        if (isButtonNegativeGone) {
            binding.buttonNegative.gone()
            val layoutParams = (binding.buttonPositive.layoutParams as ViewGroup.MarginLayoutParams)
            layoutParams.marginStart = 0
            binding.buttonPositive.layoutParams = layoutParams
        }

        if (isButtonPositiveGone) binding.buttonPositive.gone()

        initEvent()
    }

    fun setOnPositiveButtonClickListener(listener: (ConfirmationBottomSheetDialog) -> Unit) {
        onPositiveButtonClickListener = listener
    }

    fun setOnNegativeButtonClickListener(listener: (ConfirmationBottomSheetDialog) -> Unit) {
        onNegativeButtonClickListener = listener
    }

    private fun initEvent() {
        binding.buttonNegative.setOnClickListener {
            onNegativeButtonClickListener?.invoke(this)
        }

        binding.buttonPositive.setOnClickListener {
            onPositiveButtonClickListener?.invoke(this)
        }
    }
}

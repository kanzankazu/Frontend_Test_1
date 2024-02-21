package com.danamon.core.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.danamon.core.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetFragment : BottomSheetDialogFragment() {

    open fun isFullScreen(): Boolean = false
    open fun isHideAble(): Boolean = true
    open fun isDismissAble(): Boolean = true
    open fun setPeekHeight(): Int? = null

    abstract fun setLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View

    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return setLayout(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // Set full screen if required
        /* if (isFullScreen()) {
             dialog.window?.setLayout(
                 ViewGroup.LayoutParams.MATCH_PARENT,
                 ViewGroup.LayoutParams.MATCH_PARENT
             )
         }*/

        // Hideable and Dismissable settings
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            // Hideable and Dismissable settings
            bottomSheetDialog.behavior.isHideable = isHideAble()
            bottomSheetDialog.setCanceledOnTouchOutside(isDismissAble())
            // Set peek height if provided
            val peekHeight = setPeekHeight()
            if (isFullScreen()) {
//                bottomSheetDialog.behavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
                bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheetDialog.behavior.skipCollapsed = true
            } else if (peekHeight != null) {
                bottomSheetDialog.behavior.peekHeight = peekHeight
            }
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*if (isFullScreen()) setStyle(STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        else */setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    fun showDialog(activity: AppCompatActivity) {
        show(activity.supportFragmentManager, "")
    }

    fun showDialog(fragment: Fragment) {
        show(fragment.childFragmentManager, "")
    }
}
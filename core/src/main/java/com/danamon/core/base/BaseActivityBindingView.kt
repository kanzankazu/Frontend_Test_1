@file:Suppress("UNCHECKED_CAST")

package com.danamon.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Created by kanzan on 19/02/24.
 */
abstract class BaseActivityBindingView<VB : ViewBinding> : AppCompatActivity() {

    protected open fun setActivityResult() {}
    protected open fun setSubscribeToLiveData() {}
    protected open fun getBundleData() {}
    protected abstract fun setContent()
    protected abstract fun setListener()
    protected abstract fun getData()

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB
    val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        setActivityResult()
        getBundleData()
        setContent()
        setListener()
        getData()
        setSubscribeToLiveData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

@file:Suppress("UNCHECKED_CAST")

package com.danamon.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivityBindingView<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB
    val binding: VB
        get() = _binding as VB

    protected open fun setActivityResult() {}
    protected open fun setSubscribeToLiveData() {}
    protected open fun getBundleData() {}
    protected open fun setContent() {}
    protected open fun setListener() {}
    protected open fun getData() {}

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

    /**@return example R.menu.menu*/
    open fun setOptionMenu(): Int = -1

    /**when (id) { R.id.menuId -> sampleFun() }*/
    open fun setOptionMenuListener(id: Int, item: MenuItem) {}

    /**example MenuItem xxx = menu.findItem(R.id.xxx);*/
    open fun setOptionMenuValidation(menu: Menu) {}

    open fun onBackPressedListener(): Boolean = false

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (setOptionMenu() != -1) menuInflater.inflate(setOptionMenu(), menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        setOptionMenuListener(id, item)
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        setOptionMenuValidation(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

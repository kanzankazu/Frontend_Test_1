package com.danamon.feature.module.main

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import com.danamon.core.base.BaseActivityBindingView
import com.danamon.core.base.baseresponse.handleBaseResponse
import com.danamon.core.ext.addClearTaskNewTask
import com.danamon.core.ext.simpleToast
import com.danamon.core.ext.use
import com.danamon.core.ext.vmLoadDataRe
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.data.api.user.model.User
import com.danamon.data.implementation.user.mapper.toStringRole
import com.danamon.feature.R
import com.danamon.feature.databinding.ActivityMainBinding
import com.danamon.feature.navigator.LoginRegisterNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivityBindingView<ActivityMainBinding>() {

    @Inject
    lateinit var loginRegisterNavigation: LoginRegisterNavigation

    private val adapterAdmin by lazy { MainAdminAdapter() }
    private val adapterUser by lazy { MainUserAdapter() }
    private val viewModel by viewModels<MainViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    companion object;

    override fun setContent() = binding.use {
        setSupportActionBar(tbActivityMain)
    }

    override fun setOptionMenu(): Int {
        return R.menu.main_menu
    }

    override fun setOptionMenuListener(id: Int, item: MenuItem) {
        when (id) {
            R.id.main_menu_logout -> {
                viewModel.logOut()
            }
        }
    }

    override fun setSubscribeToLiveData() {
        vmLoadDataRe(viewModel.getUserData) {
            it.handleBaseResponse(
                onError = { simpleToast(it) },
                onSuccess = {
                    viewModel.userData = it
                    handleTitleSubtitle(it)
                    handleUiState(isUser = it.role == 0)
                }
            )
        }
        vmLoadDataRe(viewModel.getUserDatas) {
            it.handleBaseResponse(
                onEmpty = {},
                onError = { simpleToast(it) },
                onSuccess = {
                    val list = it.filter { it.userId != viewModel.userData?.userId }
                    adapterAdmin.setData(list as ArrayList<User>)
                }
            )
        }
        vmLoadDataRe(viewModel.getListPhoto) {
            it.handleBaseResponse(
                onError = {},
                onSuccess = {
                    adapterUser.setData(it as ArrayList<JsonPlaceHolderPhoto>)
                }
            )
        }
        vmLoadDataRe(viewModel.logout) {
            it.handleBaseResponse(
                onError = { simpleToast(it) },
                onSuccess = { gotoLogin() }
            )
        }
    }

    override fun getData() {
        viewModel.getUserData()
    }

    private fun handleTitleSubtitle(userEntity: User) = binding.use {
        tbActivityMain.title = "${userEntity.userId}, ${userEntity.toStringRole()}, ${userEntity.username}"
        tbActivityMain.subtitle = userEntity.email
    }

    private fun handleUiState(isUser: Boolean) = binding.use {
        if (isUser) {
            rvActivityMain.setRecyclerGrid(
                recyclerAdapter = adapterUser,
                spanCountGrid = 2,
                spacingItemDecoration = 8,
            )
            viewModel.getJsonPlaceHolderPhoto(1)
        } else {
            rvActivityMain.setRecyclerLinearVertical(
                recyclerAdapter = adapterAdmin,
                spacingItemDecoration = 8,
                isAll = true,
            )
            viewModel.getUserDatas()
        }
    }

    private fun gotoLogin() {
        val intent = loginRegisterNavigation.createIntentLoginRegisterActivity(this).addClearTaskNewTask()
        startActivity(intent)
    }
}
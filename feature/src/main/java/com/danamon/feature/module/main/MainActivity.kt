package com.danamon.feature.module.main

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import com.danamon.core.base.BaseActivityBindingView
import com.danamon.core.base.baseresponse.handleBaseResponse
import com.danamon.core.ext.addClearTaskNewTask
import com.danamon.core.ext.loadMoreListener
import com.danamon.core.ext.openEmail
import com.danamon.core.ext.openLinkToWeb
import com.danamon.core.ext.simpleToast
import com.danamon.core.ext.use
import com.danamon.core.ext.visibleView
import com.danamon.core.ext.vmLoadDataRe
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.data.api.user.model.User
import com.danamon.data.implementation.user.mapper.toStringRole
import com.danamon.feature.R
import com.danamon.feature.databinding.ActivityMainBinding
import com.danamon.feature.module.dialog.EditUserBottomSheetDialog
import com.danamon.feature.module.dialog.RemoveUserBottomSheetDialog
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
                onError = { s -> simpleToast(s) },
                onSuccess = { user ->
                    viewModel.userData = user
                    handleTitleSubtitle(user)
                    handleUiState(isUser = user.role == 0)
                }
            )
        }
        vmLoadDataRe(viewModel.getUserDatas) {
            it.handleBaseResponse(
                onEmpty = {},
                onError = { s -> simpleToast(s) },
                onSuccess = { users ->
                    val list = users.filter { user -> user.userId != viewModel.userData?.userId }
                    adapterAdmin.setData(list as ArrayList<User>)
                }
            )
        }
        vmLoadDataRe(viewModel.getListPhoto) {
            it.handleBaseResponse(
                onLoading = { b ->
                    if (!viewModel.isLoadMore) binding.srlActivityMain.isRefreshing = b
                    else binding.pbActivityMain.visibleView(b)
                },
                onError = { s -> simpleToast(s) },
                onSuccess = { list ->
                    if (viewModel.isLoadMore) {
                        adapterUser.addDataS(list as ArrayList<JsonPlaceHolderPhoto>)
                        viewModel.isLoadMore = false
                    } else {
                        adapterUser.setData(list as ArrayList<JsonPlaceHolderPhoto>)
                    }
                }
            )
        }
        vmLoadDataRe(viewModel.logout) {
            it.handleBaseResponse(
                onError = { s -> simpleToast(s) },
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
            adapterUser.apply {
                rvActivityMain.setRecyclerGrid(this, 2, 8)
                onItemClick = { gotoOpenLink(it) }
            }
            nsvActivityMain.loadMoreListener {
                if (!viewModel.isLoadMore) {
                    viewModel.currentPage = viewModel.currentPage + 1
                    viewModel.isLoadMore = true
                    viewModel.getJsonPlaceHolderPhoto()
                }
            }
            srlActivityMain.isActivated = true
            srlActivityMain.setOnRefreshListener {
                viewModel.currentPage = 1
                viewModel.getJsonPlaceHolderPhoto()
            }
            viewModel.getJsonPlaceHolderPhoto()
        } else {
            adapterAdmin.apply {
                rvActivityMain.setRecyclerLinearVertical(this, 8, true)
                onEditClick = { showDialogEdit(it.userId) }
                onRemoveClick = { showDialogRemove(it.userId) }
                onEmailClick = { gotoOpenEmailApp(it) }
            }
            srlActivityMain.isActivated = false
            srlActivityMain.setOnRefreshListener(null)
            viewModel.getUserDatas()
        }
    }

    private fun showDialogRemove(userId: Long) {
        RemoveUserBottomSheetDialog(userId).apply {
            onSuccessRemove = { viewModel.getUserDatas() }
            show(supportFragmentManager, "MainActivity - showDialogRemove")
        }
    }

    private fun showDialogEdit(userId: Long) {
        EditUserBottomSheetDialog(userId).apply {
            onSuccessEdit = { viewModel.getUserDatas() }
            show(supportFragmentManager, "MainActivity - showDialogEdit")
        }
    }

    private fun gotoOpenEmailApp(it: String) {
        openEmail(it, "Test", "Test aja kok")
    }

    private fun gotoOpenLink(url: String) {
        openLinkToWeb(url)
    }

    private fun gotoLogin() {
        val intent = loginRegisterNavigation.createIntentLoginRegisterActivity(this).addClearTaskNewTask()
        startActivity(intent)
    }
}
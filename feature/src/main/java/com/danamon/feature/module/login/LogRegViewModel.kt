package com.danamon.feature.module.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danamon.core.base.baseresponse.BaseResponse
import com.danamon.core.ext.get
import com.danamon.core.ext.getLaunch
import com.danamon.core.ext.toLiveData
import com.danamon.core.ext.toMediator
import com.danamon.data.api.user.repository.UserRepository
import com.danamon.data.implementation.user.local.room.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogRegViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    val isLogin = MutableLiveData(true)

    val username = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val role = MutableLiveData(-1)

    fun validationButton() = listOf(
        isLogin,
        username,
        email,
        password,
        role
    ).toMediator {
        arrayListOf<Boolean>().apply {
            if (!isLogin.get()) add(username.get().isNotEmpty())
            add(email.get().isNotEmpty())
            add(password.get().isNotEmpty())
            if (!isLogin.get()) add(role.get() > -1)
        }.all { it }
    }

    private val _loginUser = MutableLiveData<BaseResponse<Unit>>()
    val loginUser = _loginUser.toLiveData()
    fun loginUser() {
        getLaunch {
            _loginUser.postValue(userRepository.checkLogin(email.get(), password.get()))
        }
    }

    private val _registerUser = MutableLiveData<BaseResponse<String>>()
    val registerUser = _registerUser.toLiveData()
    fun registerUser() {
        getLaunch {
            _registerUser.postValue(userRepository.saveNewUser(createUserEntity()))
        }
    }

    private fun createUserEntity(): UserEntity {
        return UserEntity(
            username = username.get(),
            email = email.get(),
            password = password.get(),
            role = role.get()
        )
    }
}

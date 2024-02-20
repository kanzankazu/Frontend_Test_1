package com.danamon.danamonfrontendtest.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danamon.core.base.baseresponse.BaseResponse
import com.danamon.core.ext.getLaunch
import com.danamon.core.ext.toLiveData
import com.danamon.data.api.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _checkLogin = MutableLiveData<BaseResponse<Boolean>>()
    val checkLogin = _checkLogin.toLiveData()
    fun checkLogin() {
        getLaunch {
            _checkLogin.postValue(userRepository.checkLogin())
        }
    }
}

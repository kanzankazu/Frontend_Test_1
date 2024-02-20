package com.danamon.feature.module.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danamon.core.base.baseresponse.BaseResponse
import com.danamon.core.ext.getLaunch
import com.danamon.core.ext.toLiveData
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.data.api.user.model.User
import com.danamon.data.api.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    var userData: User? = null
    private val _getUserData = MutableLiveData<BaseResponse<User>>()
    val getUserData = _getUserData.toLiveData()
    fun getUserData() {
        getLaunch {
            _getUserData.postValue(userRepository.getUserData())
        }
    }

    private val _getListPhoto = MutableLiveData<BaseResponse<List<JsonPlaceHolderPhoto>>>()
    val getListPhoto = _getListPhoto.toLiveData()
    fun getJsonPlaceHolderPhoto(page: Int) {
        getLaunch {
            _getListPhoto.postValue(userRepository.getJsonPlaceHolderPhoto(page))
        }
    }

    private val _getUserDatas = MutableLiveData<BaseResponse<List<User>>>()
    val getUserDatas = _getUserDatas.toLiveData()
    fun getUserDatas() {
        getLaunch {
            _getUserDatas.postValue(userRepository.getUserDatas())
        }
    }

    private val _logout = MutableLiveData<BaseResponse<Unit>>()
    val logout = _logout.toLiveData()
    fun logOut() {
        getLaunch {
            _logout.postValue(userRepository.logoutUser())
        }
    }
}

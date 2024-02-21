package com.danamon.feature.ui.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danamon.core.base.baseresponse.BaseResponse
import com.danamon.core.ext.get
import com.danamon.core.ext.getLaunch
import com.danamon.core.ext.toLiveData
import com.danamon.core.ext.toMediator
import com.danamon.data.api.user.model.User
import com.danamon.data.api.user.repository.UserRepository
import com.danamon.data.implementation.user.local.room.model.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RemoveEditUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    val userId = MutableLiveData(0L)
    val username = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val role = MutableLiveData(-1)

    private val getUserDataData: User? = null
    private val _getUserData = MutableLiveData<BaseResponse<User>>()
    val getUserData = _getUserData.toLiveData()
    fun getUserData() {
        getLaunch {
            _getUserData.postValue(userRepository.getUserData(userId.get().toString()))
        }
    }

    private val removeUserData: Unit? = null
    private val _removeUser = MutableLiveData<BaseResponse<String>>()
    val removeUser = _removeUser.toLiveData()
    fun removeUser() {
        getLaunch {
            _removeUser.postValue(userRepository.removeUser(password.get(), userId.get()))
        }
    }

    private val editUserData: String? = null
    private val _editUser = MutableLiveData<BaseResponse<String>>()
    val editUser = _editUser.toLiveData()
    fun editUser() {
        getLaunch {
            _editUser.postValue(userRepository.editUser(createUserEntity()))
        }
    }

    private fun createUserEntity(): UserEntity {
        return UserEntity(
            userId = userId.get(),
            username = username.get(),
            email = email.get(),
            password = password.get(),
            role = role.get()
        )
    }

    fun validationButtonEdit() = listOf(
        userId,
        username,
        email,
        password,
        role
    ).toMediator {
        arrayListOf<Boolean>().apply {
            add(userId.get() != 0L)
            add(username.get().isNotEmpty())
            add(email.get().isNotEmpty())
            add(password.get().isNotEmpty())
            add(role.get() > -1)
        }.all { it }
    }

    fun validationButtonRemove() = listOf(
        userId,
        password,
    ).toMediator {
        arrayListOf<Boolean>().apply {
            add(userId.get() != 0L)
            add(password.get().isNotEmpty())
        }.all { it }
    }
}

package com.danamon.data.api.user.repository

import com.danamon.core.base.baseresponse.BaseResponse
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.data.api.user.model.User
import com.danamon.data.implementation.user.local.room.model.UserEntity

interface UserRepository {
    suspend fun getJsonPlaceHolderPhoto(page: Int): BaseResponse<List<JsonPlaceHolderPhoto>>
    suspend fun loginUser(email: String, password: String): BaseResponse<Unit>
    suspend fun registerUser(data: UserEntity): BaseResponse<String>
    suspend fun getUserData(): BaseResponse<User>
    suspend fun getUserDatas(): BaseResponse<List<User>>
    suspend fun checkLogin(): BaseResponse<Boolean>
    suspend fun logoutUser(): BaseResponse<Unit>
}
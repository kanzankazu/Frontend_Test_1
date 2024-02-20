package com.danamon.data.api.user.repository

import com.danamon.core.base.baseresponse.BaseResponse
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.data.implementation.user.local.room.model.UserEntity

interface UserRepository {
    suspend fun getJsonPlaceHolderPhoto(page: Int): BaseResponse<List<JsonPlaceHolderPhoto>>
    suspend fun checkLogin(email: String, password: String): BaseResponse<Unit>
    suspend fun saveNewUser(data: UserEntity): BaseResponse<String>
}
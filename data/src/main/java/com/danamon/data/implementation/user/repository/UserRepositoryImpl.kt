package com.danamon.data.implementation.user.repository

import com.danamon.core.base.baseresponse.BaseResponse
import com.danamon.core.base.baseresponse.toBaseResponseError
import com.danamon.core.base.baseresponse.toBaseResponseSuccess
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.data.api.user.repository.UserRepository
import com.danamon.data.implementation.user.local.preference.UserPreference
import com.danamon.data.implementation.user.local.room.dao.UserRequestDao
import com.danamon.data.implementation.user.local.room.model.UserEntity
import com.danamon.data.implementation.user.mapper.toJsonPlaceHolderPhoto
import com.danamon.data.implementation.user.remote.api.UserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val userRequestDao: UserRequestDao,
    private val userService: UserService,
    private val userPreference: UserPreference,
) : UserRepository {
    override suspend fun checkLogin(email: String, password: String): BaseResponse<Unit> {
        return withContext(ioDispatcher) {
            return@withContext try {
                val userEntity = userRequestDao.getData(email, password)
                if (userEntity != null) Unit.toBaseResponseSuccess()
                else "User is not registered yet, please register".toBaseResponseError()
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }

    override suspend fun saveNewUser(userEntity: UserEntity): BaseResponse<String> {
        return withContext(ioDispatcher) {
            return@withContext try {
                val id = userRequestDao.insertData(userEntity)
                if (id != -1L) "Your registration is successful, please log in".toBaseResponseSuccess()
                else "Your registration failed, please try again".toBaseResponseError()
            } catch (e: Exception) {
                val s = e.stackTraceToString()
                if (s.contains("unique", true)) {
                    "the email is already registered by someone else".toBaseResponseError()
                } else {
                    e.toBaseResponseError()
                }
            }
        }
    }

    override suspend fun getJsonPlaceHolderPhoto(page: Int): BaseResponse<List<JsonPlaceHolderPhoto>> {
        return withContext(context = ioDispatcher) {
            try {
                when (val jsonPlaceHolderPhoto = userService.jsonPlaceHolderPhoto(page)) {
                    null -> BaseResponse.Empty
                    else -> BaseResponse.Success(jsonPlaceHolderPhoto.toJsonPlaceHolderPhoto())
                }
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }
}
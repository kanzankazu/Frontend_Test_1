package com.danamon.data.implementation.user.repository

import android.content.Context
import com.danamon.core.base.baseresponse.BaseResponse
import com.danamon.core.base.baseresponse.initBaseResponseEmpty
import com.danamon.core.base.baseresponse.toBaseResponseError
import com.danamon.core.base.baseresponse.toBaseResponseSuccess
import com.danamon.data.R
import com.danamon.data.api.user.model.JsonPlaceHolderPhoto
import com.danamon.data.api.user.model.User
import com.danamon.data.api.user.repository.UserRepository
import com.danamon.data.implementation.user.local.preference.UserPreference
import com.danamon.data.implementation.user.local.room.dao.UserRequestDao
import com.danamon.data.implementation.user.local.room.model.UserEntity
import com.danamon.data.implementation.user.mapper.toJsonPlaceHolderPhoto
import com.danamon.data.implementation.user.mapper.toUser
import com.danamon.data.implementation.user.remote.api.UserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher,
    private val userRequestDao: UserRequestDao,
    private val userService: UserService,
    private val userPreference: UserPreference,
) : UserRepository {
    override suspend fun loginUser(email: String, password: String): BaseResponse<Unit> {
        return withContext(ioDispatcher) {
            return@withContext try {
                val userEntity = userRequestDao.getData(email, password)
                if (userEntity != null) {
                    userPreference.userId = userEntity.userId.toString()
                    userPreference.userName = userEntity.username
                    userPreference.userEmail = userEntity.email
                    userPreference.userRole = userEntity.role.toString()
                    Unit.toBaseResponseSuccess()
                } else context.getString(R.string.message_user_is_not_registered_yet_please_register).toBaseResponseError()
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }

    override suspend fun logoutUser(): BaseResponse<Unit> {
        return withContext(ioDispatcher) {
            return@withContext try {
                userPreference.logout()
                Unit.toBaseResponseSuccess()
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }

    override suspend fun removeUser(password: String, userId: Long): BaseResponse<String> {
        return withContext(ioDispatcher) {
            return@withContext try {
                val emailLogin = userPreference.userEmail
                val userEntity = userRequestDao.getData(emailLogin, password)
                if (userEntity != null) {
                    userRequestDao.deleteData(userId)
                    context.getString(R.string.message_data_failed_to_delete).toBaseResponseSuccess()
                } else {
                    context.getString(R.string.message_your_password_is_incorrect).toBaseResponseError()
                }
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }

    override suspend fun editUser(userEntity: UserEntity): BaseResponse<String> {
        return withContext(ioDispatcher) {
            return@withContext try {
                val id = userRequestDao.updateData(userEntity)
                if (id != 0) context.getString(R.string.message_data_changed_successfully).toBaseResponseSuccess()
                else context.getString(R.string.message_data_failed_to_change).toBaseResponseError()
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }

    override suspend fun registerUser(userEntity: UserEntity): BaseResponse<String> {
        return withContext(ioDispatcher) {
            return@withContext try {
                val id = userRequestDao.insertData(userEntity)
                if (id != -1L) context.getString(R.string.message_your_registration_is_successful_please_log_in).toBaseResponseSuccess()
                else context.getString(R.string.message_your_registration_failed_please_try_again).toBaseResponseError()
            } catch (e: Exception) {
                val s = e.stackTraceToString()
                if (s.contains("unique", true)) {
                    context.getString(R.string.message_the_email_is_already_registered_by_someone_else).toBaseResponseError()
                } else {
                    e.toBaseResponseError()
                }
            }
        }
    }

    override suspend fun getUserData(userIdTarget: String): BaseResponse<User> {
        return withContext(ioDispatcher) {
            return@withContext try {
                val userId = userIdTarget.ifEmpty { userPreference.userId }
                if (userId.isNotEmpty()) {
                    userRequestDao.getData(userId.toLong())?.toUser()?.toBaseResponseSuccess() ?: kotlin.run {
                        context.getString(R.string.label_user_not_found).toBaseResponseError()
                    }
                } else {
                    context.getString(R.string.label_user_not_found).toBaseResponseError()
                }
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }

    override suspend fun getUserDatas(): BaseResponse<List<User>> {
        return withContext(ioDispatcher) {
            return@withContext try {
                val list = userRequestDao.getList().map { it.toUser() }
                if (list.isNotEmpty()) list.toBaseResponseSuccess()
                else initBaseResponseEmpty()
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }

    override suspend fun checkLogin(): BaseResponse<Boolean> {
        return withContext(ioDispatcher) {
            return@withContext try {
                userPreference.userId.isNotEmpty().toBaseResponseSuccess()
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }

    override suspend fun getJsonPlaceHolderPhoto(page: Int, limit: Int): BaseResponse<List<JsonPlaceHolderPhoto>> {
        return withContext(context = ioDispatcher) {
            try {
                when (val jsonPlaceHolderPhoto = userService.jsonPlaceHolderPhoto(page, limit)) {
                    null -> initBaseResponseEmpty()
                    else -> jsonPlaceHolderPhoto.toJsonPlaceHolderPhoto().toBaseResponseSuccess()
                }
            } catch (e: Exception) {
                e.toBaseResponseError()
            }
        }
    }
}
package com.danamon.data.di

import com.danamon.data.api.user.repository.UserRepository
import com.danamon.data.dispatcher.IoDispatcher
import com.danamon.data.implementation.user.local.preference.UserPreference
import com.danamon.data.implementation.user.local.room.dao.UserRequestDao
import com.danamon.data.implementation.user.remote.api.UserService
import com.danamon.data.implementation.user.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providesDefaultDispatcher(
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        userRequestDao: UserRequestDao,
        userService: UserService,
        userPreference: UserPreference,
    ): UserRepository = UserRepositoryImpl(
        coroutineDispatcher,
        userRequestDao,
        userService,
        userPreference
    )
}

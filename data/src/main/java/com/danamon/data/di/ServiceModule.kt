package com.danamon.data.di

import com.danamon.data.implementation.user.remote.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideUserApi(
        retrofit: Retrofit,
    ): UserService = retrofit.create(UserService::class.java)
}

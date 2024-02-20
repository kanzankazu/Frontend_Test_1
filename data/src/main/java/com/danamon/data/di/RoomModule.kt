package com.danamon.data.di

import android.content.Context
import com.danamon.data.implementation.user.local.room.database.UserDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideUserDB(
        @ApplicationContext context: Context,
    ) = UserDB.getInstance(context)

    @Provides
    @Singleton
    fun provideUserRequestDao(
        userDB: UserDB,
    ) = userDB.userRequestDao()
}
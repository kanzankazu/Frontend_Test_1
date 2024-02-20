package com.danamon.data.di

import android.content.Context
import com.danamon.data.implementation.user.local.preference.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    fun provideUserPreference(
        context: Context,
    ) = UserPreference(context)
}
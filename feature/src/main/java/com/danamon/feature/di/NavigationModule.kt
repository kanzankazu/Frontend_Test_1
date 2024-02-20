package com.danamon.feature.di

import android.content.Context
import com.danamon.core.ext.makeIntent
import com.danamon.feature.module.auth.LoginRegisterActivity
import com.danamon.feature.module.main.MainActivity
import com.danamon.feature.navigator.LoginRegisterNavigation
import com.danamon.feature.navigator.MainNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideLoginRegisterNavigation(
    ) = object : LoginRegisterNavigation {
        override fun createIntentLoginRegisterActivity(context: Context) =
            context.makeIntent<LoginRegisterActivity>()
    }

    @Provides
    fun provideMainNavigation(
    ) = object : MainNavigation {
        override fun createIntentMainActivity(context: Context) =
            context.makeIntent<MainActivity>()
    }
}
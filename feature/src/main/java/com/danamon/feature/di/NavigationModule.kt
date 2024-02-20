package com.danamon.feature.di

import android.content.Context
import com.danamon.core.ext.makeIntent
import com.danamon.feature.module.login.LoginRegisterActivity
import com.danamon.feature.navigator.LoginRegisterNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideNavigationModuleRepository(
    ) = object : LoginRegisterNavigation {
        override fun createIntentLoginRegisterActivity(context: Context) =
            context.makeIntent<LoginRegisterActivity>()
    }
}
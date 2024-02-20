package com.danamon.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.danamon.core.ext.isDebug
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGsonConverterFactory() = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun getChuckerInterceptor(
        context: Context,
    ) = ChuckerInterceptor.Builder(context).build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)

        httpBuilder.apply {
            if (isDebug()) {
                httpBuilder.addInterceptor(loggingInterceptor)
                httpBuilder.addInterceptor(chuckerInterceptor)
            } else {
                if (isDebug()) addInterceptor(chuckerInterceptor)
            }
        }

        return httpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: GsonConverterFactory,
        client: OkHttpClient,
    ) =
        Retrofit.Builder()
            .baseUrl("https://example.com")
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
}

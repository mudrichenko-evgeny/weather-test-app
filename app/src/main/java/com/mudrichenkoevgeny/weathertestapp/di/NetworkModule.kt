package com.mudrichenkoevgeny.weathertestapp.di

import android.content.Context
import com.mudrichenkoevgeny.weathertestapp.data.network.networkchecker.NetworkChecker
import com.mudrichenkoevgeny.weathertestapp.data.network.networkchecker.NetworkCheckerImpl
import com.mudrichenkoevgeny.weathertestapp.data.network.restapi.ForecastRestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_API_URL = "http://api.weatherapi.com/"
    const val RETROFIT_CONVERTER_CONTENT_TYPE = "application/json"

    @Provides
    @Singleton
    fun provideNetworkChecker(
        @ApplicationContext context: Context
    ): NetworkChecker {
        return NetworkCheckerImpl(
            context
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    RETROFIT_CONVERTER_CONTENT_TYPE.toMediaType()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideForecastRestApi(
        retrofit: Retrofit
    ): ForecastRestApi {
        return retrofit.create(ForecastRestApi::class.java)
    }
}
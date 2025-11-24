package com.mudrichenkoevgeny.weathertestapp.di

import com.mudrichenkoevgeny.weathertestapp.data.network.networkchecker.NetworkChecker
import com.mudrichenkoevgeny.weathertestapp.data.network.restapi.ForecastRestApi
import com.mudrichenkoevgeny.weathertestapp.data.repository.ForecastRepositoryImpl
import com.mudrichenkoevgeny.weathertestapp.domain.repository.ForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideForecastRepository(
        networkChecker: NetworkChecker,
        forecastRestApi: ForecastRestApi
    ): ForecastRepository {
        return ForecastRepositoryImpl(
            networkChecker = networkChecker,
            forecastRestApi = forecastRestApi
        )
    }
}
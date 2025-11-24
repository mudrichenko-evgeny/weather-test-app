package com.mudrichenkoevgeny.weathertestapp.data.repository

import com.mudrichenkoevgeny.weathertestapp.BuildConfig
import com.mudrichenkoevgeny.weathertestapp.data.exception.NoInternetException
import com.mudrichenkoevgeny.weathertestapp.data.mapper.toDomain
import com.mudrichenkoevgeny.weathertestapp.data.network.networkchecker.NetworkChecker
import com.mudrichenkoevgeny.weathertestapp.data.network.restapi.ForecastRestApi
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastData
import com.mudrichenkoevgeny.weathertestapp.domain.repository.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val networkChecker: NetworkChecker,
    private val forecastRestApi: ForecastRestApi
) : ForecastRepository {

    companion object {
        const val LOCATION_MOSCOW = "55.7569,37.6151"
        const val DAYS = 3
        const val LANG = "ru"
    }

    override suspend fun getForecast(): Result<ForecastData> = withContext(Dispatchers.IO) {
        if (!networkChecker.isInternetAvailable()) {
            return@withContext Result.failure(NoInternetException())
        }

        try {
            val forecastResponseDto = forecastRestApi.getForecast(
                apiKey = BuildConfig.WEATHER_API_KEY,
                location = LOCATION_MOSCOW,
                days = DAYS,
                lang = LANG
            )

            Result.success(forecastResponseDto.toDomain())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
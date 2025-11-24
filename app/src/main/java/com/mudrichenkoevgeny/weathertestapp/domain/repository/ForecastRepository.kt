package com.mudrichenkoevgeny.weathertestapp.domain.repository

import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastData

interface ForecastRepository {
    suspend fun getForecast(): Result<ForecastData>
}
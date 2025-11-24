package com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast

import com.mudrichenkoevgeny.weathertestapp.domain.error.AppError
import com.mudrichenkoevgeny.weathertestapp.domain.model.Forecast
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastDay
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastHour

sealed interface ForecastUiState {
    object Loading : ForecastUiState
    data class Content(
        val locationName: String,
        val currentForecast: Forecast,
        val todayForecast: List<ForecastHour>,
        val daysForecast: List<ForecastDay>
    ) : ForecastUiState
    data class Error(val appError: AppError) : ForecastUiState
}
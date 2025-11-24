package com.mudrichenkoevgeny.weathertestapp.domain.model

data class ForecastHour(
    val timeEpoch: Long,
    val forecast: Forecast
)
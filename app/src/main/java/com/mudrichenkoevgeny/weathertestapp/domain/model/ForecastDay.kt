package com.mudrichenkoevgeny.weathertestapp.domain.model

data class ForecastDay(
    val dateEpoch: Long,
    val forecast: Forecast,
    val forecastHourList: List<ForecastHour>
)
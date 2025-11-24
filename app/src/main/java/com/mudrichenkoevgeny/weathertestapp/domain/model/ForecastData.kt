package com.mudrichenkoevgeny.weathertestapp.domain.model

data class ForecastData(
    val locationName: String,
    val current: Forecast,
    val forecastDayList: List<ForecastDay>
) {

    fun getTodayForecast(): List<ForecastHour> = forecastDayList
        .firstOrNull()
        ?.forecastHourList
        ?: emptyList()
}
package com.mudrichenkoevgeny.weathertestapp.domain.model.mock

import com.mudrichenkoevgeny.weathertestapp.core.extensions.oneDecimal
import com.mudrichenkoevgeny.weathertestapp.domain.model.Forecast
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastDay
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastHour

object ForecastMock {

    const val LOCATION_NAME: String = "Moscow"

    private val conditions: Map<String, String> = mapOf(
        "Sunny" to "//cdn.weatherapi.com/weather/64x64/day/113.png",
        "Partly cloudy" to "//cdn.weatherapi.com/weather/64x64/day/116.png",
        "Cloudy" to "//cdn.weatherapi.com/weather/64x64/day/119.png",
        "Overcast" to "//cdn.weatherapi.com/weather/64x64/day/122.png",
        "Mist" to "//cdn.weatherapi.com/weather/64x64/day/143.png"
    )

    fun getForecast(): Forecast {
        val (text, icon) = conditions.entries.random().let { it.key to it.value }

        return Forecast(
            temperature = ((10..25).random() + Math.random()).oneDecimal(),
            windSpeed = ((1..10).random() / 10.0).oneDecimal(),
            precipitation = ((0..10).random() / 10.0).oneDecimal(),
            conditionText = text,
            conditionIconUrl = icon
        )
    }

    fun getTodayForecast(count: Int = 12): List<ForecastHour> {
        val start = System.currentTimeMillis() / 1000

        return List(count) { index ->
            ForecastHour(
                timeEpoch = start + index * 3600,
                forecast = getForecast()
            )
        }
    }

    fun getDaysForecast(count: Int = 3): List<ForecastDay> {
        val daySeconds = 24 * 3600

        return List(count) { index ->
            val dateEpoch = (System.currentTimeMillis() / 1000) + index * daySeconds
            ForecastDay(
                dateEpoch = dateEpoch,
                forecast = getForecast(),
                forecastHourList = getTodayForecast(24)
            )
        }
    }

}
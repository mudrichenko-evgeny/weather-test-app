package com.mudrichenkoevgeny.weathertestapp.data.mapper

import com.mudrichenkoevgeny.weathertestapp.data.network.model.ForecastDayDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.HourDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.response.ForecastResponseDto
import com.mudrichenkoevgeny.weathertestapp.domain.model.Forecast
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastData
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastDay
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastHour

fun ForecastResponseDto.toDomain(): ForecastData {
    return ForecastData(
        locationName = this.location.name,
        current = Forecast(
            temperature = this.current.tempC,
            windSpeed = this.current.windKph,
            precipitation = this.current.precipMm,
            conditionText = this.current.condition.text,
            conditionIconUrl = "https:${this.current.condition.icon}"
        ),
        forecastDayList = this.forecast.forecastDay.map { forecastDay ->
            forecastDay.toDomain()
        }
    )
}

private fun ForecastDayDto.toDomain(): ForecastDay {
    return ForecastDay(
        dateEpoch = this.dateEpoch,
        forecast = Forecast(
            temperature = this.day.avgTempC,
            windSpeed = this.day.maxWindKph,
            precipitation = this.day.totalPrecipMm,
            conditionText = this.day.condition.text,
            conditionIconUrl = "https:${this.day.condition.icon}"
        ),
        forecastHourList = this.hour.map { forecastHour ->
            forecastHour.toDomain()
        },
    )
}

private fun HourDto.toDomain(): ForecastHour {
    return ForecastHour(
        timeEpoch = this.timeEpoch,
        forecast = Forecast(
            temperature = this.tempC,
            windSpeed = this.windKph,
            precipitation = this.precipMm,
            conditionText = this.condition.text,
            conditionIconUrl = "https:${this.condition.icon}"
        )
    )
}
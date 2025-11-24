package com.mudrichenkoevgeny.weathertestapp.domain.model

/**
 * Domain model representing weather forecast data.
 *
 * @property temperature Temperature in Celsius.
 * @property windSpeed Wind speed in kilometers per hour (kph).
 * @property precipitation Precipitation amount in millimeters (mm).
 * @property conditionText Text description of the weather condition, e.g., "Sunny", "Cloudy".
 * @property conditionIconUrl URL of the icon representing the weather condition.
 */
data class Forecast(
    val temperature: Double,
    val windSpeed: Double,
    val precipitation: Double,
    val conditionText: String,
    val conditionIconUrl: String
)
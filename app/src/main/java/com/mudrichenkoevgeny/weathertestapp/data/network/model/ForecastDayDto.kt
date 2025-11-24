package com.mudrichenkoevgeny.weathertestapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayDto(
    @SerialName("date") val date: String,
    @SerialName("date_epoch") val dateEpoch: Long,
    @SerialName("day") val day: DayDto,
    @SerialName("astro") val astro: AstroDto,
    @SerialName("hour") val hour: List<HourDto>
)
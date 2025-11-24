package com.mudrichenkoevgeny.weathertestapp.data.network.model.response

import com.mudrichenkoevgeny.weathertestapp.data.network.model.CurrentDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.ForecastDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.LocationDto
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponseDto(
    val location: LocationDto,
    val current: CurrentDto,
    val forecast: ForecastDto
)
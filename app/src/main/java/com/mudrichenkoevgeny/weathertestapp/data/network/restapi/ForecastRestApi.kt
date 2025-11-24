package com.mudrichenkoevgeny.weathertestapp.data.network.restapi

import com.mudrichenkoevgeny.weathertestapp.data.network.model.response.ForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastRestApi {

    @GET("v1/forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") lang: String,
    ): ForecastResponseDto
}
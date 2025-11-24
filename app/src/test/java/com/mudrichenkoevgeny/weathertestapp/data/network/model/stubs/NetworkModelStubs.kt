package com.mudrichenkoevgeny.weathertestapp.data.network.model.stubs

import com.mudrichenkoevgeny.weathertestapp.data.network.model.AstroDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.ConditionDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.CurrentDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.DayDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.ForecastDayDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.ForecastDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.HourDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.LocationDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.response.ForecastResponseDto

fun createConditionDtoStub() = ConditionDto(
    text = "Clear",
    icon = "",
    code = 1000
)

fun createLocationDtoStub() = LocationDto(
    name = "Moscow",
    region = "Moscow",
    country = "Russia",
    lat = 55.75,
    lon = 37.61,
    tzId = "Europe/Moscow",
    localtimeEpoch = 1698288000L,
    localtime = "2025-11-24 12:00"
)

fun createCurrentDtoStub() = CurrentDto(
    lastUpdatedEpoch = 1698288000L,
    lastUpdated = "2025-11-24 12:00",
    tempC = 20.0,
    tempF = 68.0,
    isDay = 1,
    condition = createConditionDtoStub(),
    windMph = 5.0,
    windKph = 8.0,
    windDegree = 90,
    windDir = "E",
    pressureMb = 1013.0,
    pressureIn = 29.91,
    precipMm = 0.0,
    precipIn = 0.0,
    humidity = 50,
    cloud = 0,
    feelslikeC = 20.0,
    feelslikeF = 68.0,
    windchillC = 20.0,
    windchillF = 68.0,
    heatindexC = 20.0,
    heatindexF = 68.0,
    dewpointC = 10.0,
    dewpointF = 50.0,
    visKm = 10.0,
    visMiles = 6.0,
    uv = 5.0,
    gustMph = 7.0,
    gustKph = 11.0
)

fun createHourDtoStub() = HourDto(
    timeEpoch = 1698288000L,
    time = "2025-11-24 12:00",
    tempC = 20.0,
    tempF = 68.0,
    isDay = 1,
    condition = createConditionDtoStub(),
    windMph = 5.0,
    windKph = 8.0,
    windDegree = 90,
    windDir = "E",
    pressureMb = 1013.0,
    pressureIn = 29.91,
    precipMm = 0.0,
    precipIn = 0.0,
    snowCm = 0.0,
    humidity = 50,
    cloud = 0,
    feelslikeC = 20.0,
    feelslikeF = 68.0,
    windchillC = 20.0,
    windchillF = 68.0,
    heatindexC = 20.0,
    heatindexF = 68.0,
    dewpointC = 10.0,
    dewpointF = 50.0,
    willItRain = 0,
    chanceOfRain = 0,
    willItSnow = 0,
    chanceOfSnow = 0,
    visKm = 10.0,
    visMiles = 6.0,
    gustMph = 7.0,
    gustKph = 11.0,
    uv = 5.0
)

fun createDayDtoStub() = DayDto(
    maxTempC = 20.0,
    maxTempF = 68.0,
    minTempC = 10.0,
    minTempF = 50.0,
    avgTempC = 15.0,
    avgTempF = 59.0,
    maxWindMph = 10.0,
    maxWindKph = 16.0,
    totalPrecipMm = 0.0,
    totalPrecipIn = 0.0,
    totalSnowCm = 0.0,
    avgVisKm = 10.0,
    avgVisMiles = 6.0,
    avgHumidity = 50,
    dailyWillItRain = 0,
    dailyChanceOfRain = 0,
    dailyWillItSnow = 0,
    dailyChanceOfSnow = 0,
    condition = createConditionDtoStub(),
    uv = 5.0
)

fun createAstroDtoStub() = AstroDto(
    sunrise = "07:00 AM",
    sunset = "04:00 PM",
    moonrise = "06:00 PM",
    moonset = "06:00 AM",
    moonPhase = "Waxing Crescent",
    moonIllumination = 25,
    isMoonUp = 0,
    isSunUp = 1
)

fun createForecastDayDtoStub() = ForecastDayDto(
    date = "2025-11-24",
    dateEpoch = 1698288000L,
    day = createDayDtoStub(),
    astro = createAstroDtoStub(),
    hour = listOf(createHourDtoStub())
)

fun createForecastDtoStub() = ForecastDto(
    forecastDay = listOf(createForecastDayDtoStub())
)

fun createForecastResponseDtoStub() = ForecastResponseDto(
    location = createLocationDtoStub(),
    current = createCurrentDtoStub(),
    forecast = createForecastDtoStub()
)
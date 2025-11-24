package com.mudrichenkoevgeny.weathertestapp.core.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale

fun Long.toHourText(): String {
    val instant = Instant.ofEpochSecond(this)
    val zoned = instant.atZone(ZoneId.systemDefault())
    val hour = zoned.hour
    return String.format(Locale.getDefault(), "%02d:00", hour)
}

fun Long.toDayShortText(): String {
    val sdf = SimpleDateFormat("E dd", Locale.getDefault())
    return sdf.format(Date(this * 1000))
}
package com.mudrichenkoevgeny.weathertestapp.core.extensions

import kotlin.math.round

fun Double.oneDecimal(): Double = round(this * 10) / 10
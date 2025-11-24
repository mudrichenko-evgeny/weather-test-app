package com.mudrichenkoevgeny.weathertestapp.domain.error

import com.mudrichenkoevgeny.weathertestapp.data.exception.NoInternetException

fun Throwable.toAppError(): AppError = when (this) {
    is NoInternetException -> AppError.NoInternet
    else -> AppError.Unknown
}
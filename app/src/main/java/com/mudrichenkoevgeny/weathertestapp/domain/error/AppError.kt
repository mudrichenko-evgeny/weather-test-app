package com.mudrichenkoevgeny.weathertestapp.domain.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mudrichenkoevgeny.weathertestapp.R

sealed class AppError {
    object NoInternet : AppError()
    object Unknown : AppError()
}

@Composable
fun AppError.toText(): String {
    return when (this) {
        AppError.NoInternet -> stringResource(R.string.app_error_no_internet)
        AppError.Unknown -> stringResource(R.string.app_error_unknown)
    }
}
package com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast

import com.mudrichenkoevgeny.weathertestapp.domain.error.AppError

sealed class ForecastUiEvent {
    data class ShowErrorDialog(val appError: AppError) : ForecastUiEvent()
}
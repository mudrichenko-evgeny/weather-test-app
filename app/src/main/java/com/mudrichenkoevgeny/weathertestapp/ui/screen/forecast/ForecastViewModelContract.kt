package com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ForecastViewModelContract {
    val uiState: StateFlow<ForecastUiState>
    val uiEvent: SharedFlow<ForecastUiEvent>
    fun loadForecast()
}
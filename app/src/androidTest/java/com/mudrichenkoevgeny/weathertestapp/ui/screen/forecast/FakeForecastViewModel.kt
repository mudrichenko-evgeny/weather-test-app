package com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class FakeForecastViewModel : ForecastViewModelContract {
    private val _uiState = MutableStateFlow<ForecastUiState>(ForecastUiState.Loading)
    override val uiState: StateFlow<ForecastUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<ForecastUiEvent>()
    override val uiEvent: SharedFlow<ForecastUiEvent> = _uiEvent

    var loadForecastCallCount = 0
    override fun loadForecast() { loadForecastCallCount++ }

    fun emitUiState(state: ForecastUiState) { _uiState.value = state }
    suspend fun emitUiEvent(event: ForecastUiEvent) { _uiEvent.emit(event) }
}

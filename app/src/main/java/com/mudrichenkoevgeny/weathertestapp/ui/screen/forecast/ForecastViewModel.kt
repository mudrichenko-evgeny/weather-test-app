package com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudrichenkoevgeny.weathertestapp.domain.error.toAppError
import com.mudrichenkoevgeny.weathertestapp.domain.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel(), ForecastViewModelContract {

    private val _uiState = MutableStateFlow<ForecastUiState>(ForecastUiState.Loading)
    override val uiState: StateFlow<ForecastUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ForecastUiEvent>()
    override val uiEvent: SharedFlow<ForecastUiEvent> = _uiEvent.asSharedFlow()

    override fun loadForecast() {
        viewModelScope.launch {
            _uiState.value = ForecastUiState.Loading

            forecastRepository.getForecast().fold(
                onSuccess = { data ->
                    val currentEpoch = System.currentTimeMillis() / 1000

                    _uiState.value = ForecastUiState.Content(
                        locationName = data.locationName,
                        currentForecast = data.current,
                        todayForecast = data.getTodayForecast()
                            .filter { it.timeEpoch >= currentEpoch },
                        daysForecast = data.forecastDayList
                    )
                },
                onFailure = { throwable ->
                    val appError = throwable.toAppError()
                    _uiEvent.emit(ForecastUiEvent.ShowErrorDialog(appError))
                    _uiState.value = ForecastUiState.Error(appError)
                }
            )
        }
    }
}
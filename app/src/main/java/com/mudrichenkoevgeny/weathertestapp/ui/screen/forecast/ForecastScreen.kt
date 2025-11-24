package com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudrichenkoevgeny.weathertestapp.R
import com.mudrichenkoevgeny.weathertestapp.domain.error.AppError
import com.mudrichenkoevgeny.weathertestapp.domain.error.toText
import com.mudrichenkoevgeny.weathertestapp.domain.model.Forecast
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastDay
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastHour
import com.mudrichenkoevgeny.weathertestapp.domain.model.mock.ForecastMock
import com.mudrichenkoevgeny.weathertestapp.ui.component.CurrentForecastCard
import com.mudrichenkoevgeny.weathertestapp.ui.component.DaysForecastCard
import com.mudrichenkoevgeny.weathertestapp.ui.component.TodayForecastCard
import com.mudrichenkoevgeny.weathertestapp.ui.theme.AppTheme

@Composable
fun ForecastScreen(
    viewModel: ForecastViewModelContract
) {
    val showErrorDialogEvent: MutableState<ForecastUiEvent.ShowErrorDialog?> =
        remember { mutableStateOf(null) }

    val screenUiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.loadForecast()

        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is ForecastUiEvent.ShowErrorDialog -> {
                    showErrorDialogEvent.value = uiEvent
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            showErrorDialogEvent.value?.let { errorDialog ->
                AlertDialog(
                    onDismissRequest = { showErrorDialogEvent.value = null },
                    title = { Text(text = "Error") },
                    text = { Text(text = errorDialog.appError.toText()) },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.loadForecast()
                            showErrorDialogEvent.value = null
                        }) {
                            Text("Retry")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showErrorDialogEvent.value = null }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            ForecastScreenUI(
                screenUiState = screenUiState,
                onRefreshClicked = { viewModel.loadForecast() }
            )
        }
    }
}

@Composable
private fun ForecastScreenUI(
    screenUiState: ForecastUiState,
    onRefreshClicked:() -> Unit
) {
    when (screenUiState) {
        is ForecastUiState.Loading -> {
            ForecastLoadingScreen()
        }
        is ForecastUiState.Content -> {
            ForecastContentScreen(
                locationName = screenUiState.locationName,
                currentForecast = screenUiState.currentForecast,
                todayForecast = screenUiState.todayForecast,
                daysForecast = screenUiState.daysForecast,
                onRefreshClicked = onRefreshClicked
            )
        }
        is ForecastUiState.Error -> {
            ForecastErrorScreen(
                errorText = screenUiState.appError.toText(),
                onRefreshClicked = onRefreshClicked
            )
        }
    }
}

@Composable
private fun ForecastContentScreen(
    locationName: String,
    currentForecast: Forecast,
    todayForecast: List<ForecastHour>,
    daysForecast: List<ForecastDay>,
    onRefreshClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        CurrentForecastCard(
            locationName = locationName,
            forecast = currentForecast,
            onRefreshClicked = onRefreshClicked
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        TodayForecastCard(
            todayForecast = todayForecast
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        DaysForecastCard(
            daysForecast = daysForecast
        )
    }
}

@Composable
private fun ForecastLoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CircularProgressIndicator(
                modifier = Modifier.size(56.dp),
                strokeWidth = 6.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.loading_in_progress),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ForecastErrorScreen(
    errorText: String,
    onRefreshClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Filled.ErrorOutline,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = errorText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onRefreshClicked) {
                Text(text = stringResource(R.string.refresh))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenWithContentPreview() {
    AppTheme {
        ForecastScreenUI(
            screenUiState = ForecastUiState.Content(
                locationName = ForecastMock.LOCATION_NAME,
                currentForecast = ForecastMock.getForecast(),
                todayForecast = listOf(),
                daysForecast = listOf()
            ),
            onRefreshClicked = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenWithLoadingPreview() {
    AppTheme {
        ForecastScreenUI(
            screenUiState = ForecastUiState.Loading,
            onRefreshClicked = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ForecastScreenWithErrorPreview() {
    AppTheme {
        ForecastScreenUI(
            screenUiState = ForecastUiState.Error(AppError.NoInternet),
            onRefreshClicked = { }
        )
    }
}
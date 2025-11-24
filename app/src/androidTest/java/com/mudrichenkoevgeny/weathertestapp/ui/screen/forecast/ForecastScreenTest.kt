package com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mudrichenkoevgeny.weathertestapp.domain.error.AppError
import com.mudrichenkoevgeny.weathertestapp.domain.model.mock.ForecastMock
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var fakeViewModel: FakeForecastViewModel

    @Before
    fun setup() {
        fakeViewModel = FakeForecastViewModel()
    }

    @Test
    fun forecastScreen_displaysLoading_whenUiStateLoading() {
        fakeViewModel.emitUiState(ForecastUiState.Loading)

        composeRule.setContent {
            ForecastScreen(viewModel = fakeViewModel)
        }

        composeRule.onNodeWithText("Loading...").assertIsDisplayed()
    }

    @Test
    fun forecastScreen_displaysContent_whenUiStateContent() {
        val currentForecast = ForecastMock.getForecast()
        val todayForecast = ForecastMock.getTodayForecast()
        val daysForecast = ForecastMock.getDaysForecast()

        fakeViewModel.emitUiState(
            ForecastUiState.Content(
                locationName = ForecastMock.LOCATION_NAME,
                currentForecast = currentForecast,
                todayForecast = todayForecast,
                daysForecast = daysForecast
            )
        )

        composeRule.setContent {
            ForecastScreen(viewModel = fakeViewModel)
        }

        composeRule.onNodeWithText(ForecastMock.LOCATION_NAME).assertIsDisplayed()
        composeRule.onAllNodesWithText(currentForecast.conditionText)
            .onFirst()
            .assertIsDisplayed()
        composeRule.onAllNodesWithText(todayForecast.first().forecast.conditionText)
            .onFirst()
            .assertIsDisplayed()
        composeRule.onAllNodesWithText(daysForecast.first().forecast.conditionText)
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun forecastScreen_displaysErrorDialog_whenUiEventShowErrorDialog() = runTest {
        val appError = AppError.Unknown

        composeRule.setContent {
            ForecastScreen(viewModel = fakeViewModel)
        }

        fakeViewModel.emitUiEvent(ForecastUiEvent.ShowErrorDialog(appError))

        composeRule.waitUntil(timeoutMillis = 3000) {
            composeRule.onAllNodesWithText("Error").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Error").assertIsDisplayed()
        composeRule.onNodeWithText("Retry").assertIsDisplayed()
        composeRule.onNodeWithText("Cancel").assertIsDisplayed()
    }

    @Test
    fun refreshButton_callsLoadForecast() {
        val currentForecast = ForecastMock.getForecast()
        val todayForecast = ForecastMock.getTodayForecast()
        val daysForecast = ForecastMock.getDaysForecast()

        fakeViewModel.emitUiState(
            ForecastUiState.Content(
                locationName = ForecastMock.LOCATION_NAME,
                currentForecast = currentForecast,
                todayForecast = todayForecast,
                daysForecast = daysForecast
            )
        )

        composeRule.setContent {
            ForecastScreen(viewModel = fakeViewModel)
        }

        composeRule.onAllNodesWithContentDescription("Refresh")
            .onFirst()
            .performClick()

        assert(fakeViewModel.loadForecastCallCount > 0)
    }
}

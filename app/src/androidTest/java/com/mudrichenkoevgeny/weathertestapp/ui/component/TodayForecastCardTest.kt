package com.mudrichenkoevgeny.weathertestapp.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mudrichenkoevgeny.weathertestapp.R
import com.mudrichenkoevgeny.weathertestapp.core.utils.toHourText
import com.mudrichenkoevgeny.weathertestapp.domain.model.mock.ForecastMock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodayForecastCardTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun card_displays_title() {
        val forecast = ForecastMock.getTodayForecast()
        var titleText = ""

        composeRule.setContent {
            MaterialTheme {
                titleText = stringResource(R.string.forecast_hourly)
                TodayForecastCardUI(todayForecast = forecast)
            }
        }

        composeRule
            .onNodeWithText(titleText)
            .assertExists()
    }

    @Test
    fun card_displays_all_hours() {
        // GIVEN
        val forecast = ForecastMock.getTodayForecast(count = 4)

        composeRule.setContent {
            MaterialTheme {
                TodayForecastCardUI(todayForecast = forecast)
            }
        }

        // THEN
        composeRule
            .onAllNodesWithTag("TodayHourItem")
            .assertCountEquals(forecast.size)
    }

    @Test
    fun hour_item_displays_temperature_and_time() {
        val hour = ForecastMock.getTodayForecast().first()
        var tempText = ""

        composeRule.setContent {
            MaterialTheme {
                tempText = stringResource(R.string.forecast_temperature_short, hour.forecast.temperature)
                TodayForecastHourItem(hour)
            }
        }

        composeRule
            .onNodeWithText(tempText)
            .assertExists()

        composeRule
            .onNodeWithText(hour.timeEpoch.toHourText())
            .assertExists()
    }

    @Test
    fun hour_item_displays_wind_and_precipitation() {
        val hour = ForecastMock.getTodayForecast().first()
        var windText = ""
        var precipText = ""

        composeRule.setContent {
            MaterialTheme {
                windText = stringResource(R.string.forecast_wind_short, hour.forecast.windSpeed)
                precipText = stringResource(R.string.forecast_precipitation_short, hour.forecast.precipitation)
                TodayForecastHourItem(hour)
            }
        }

        composeRule
            .onNodeWithText(windText)
            .assertExists()

        composeRule
            .onNodeWithText(precipText)
            .assertExists()
    }
}

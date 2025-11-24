package com.mudrichenkoevgeny.weathertestapp.ui.component

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mudrichenkoevgeny.weathertestapp.R
import com.mudrichenkoevgeny.weathertestapp.domain.model.mock.ForecastMock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DaysForecastCardTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun daysForecastCard_displaysHeaderAndItems() {
        // GIVEN
        val context = ApplicationProvider.getApplicationContext<Context>()
        val days = ForecastMock.getDaysForecast(count = 3)
        val first = days.first()

        composeRule.setContent {
            MaterialTheme {
                DaysForecastCard(daysForecast = days)
            }
        }

        // THEN
        composeRule
            .onNodeWithText(context.getString(R.string.forecast_daily))
            .assertIsDisplayed()

        composeRule
            .onAllNodesWithContentDescription(first.forecast.conditionText)
            .assertCountEquals(1)

        composeRule
            .onNodeWithText(
                context.getString(
                    R.string.forecast_temperature_short,
                    first.forecast.temperature
                )
            )
            .assertIsDisplayed()

        composeRule
            .onNodeWithText(first.forecast.conditionText)
            .assertIsDisplayed()
    }
}
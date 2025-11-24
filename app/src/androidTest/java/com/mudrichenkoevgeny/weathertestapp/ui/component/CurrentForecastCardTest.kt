package com.mudrichenkoevgeny.weathertestapp.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mudrichenkoevgeny.weathertestapp.domain.model.mock.ForecastMock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrentForecastCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val forecast = ForecastMock.getForecast()

    @Test
    fun currentForecastCard_displaysTextsCorrectly() {
        // GIVEN
        val locationName = "Moscow"

        composeTestRule.setContent {
            CurrentForecastCard(
                locationName = locationName,
                forecast = forecast,
                onRefreshClicked = {}
            )
        }

        // THEN
        composeTestRule.onNodeWithText(locationName).assertIsDisplayed()
        composeTestRule.onNodeWithText("20.0°C").assertIsDisplayed()
        composeTestRule.onNodeWithText(forecast.conditionText).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Refresh").assertIsDisplayed()
    }

    @Test
    fun currentForecastCard_refreshButton_callsCallback() {
        // GIVEN
        var clicked = false
        composeTestRule.setContent {
            CurrentForecastCard(
                locationName = "Moscow",
                forecast = forecast,
                onRefreshClicked = { clicked = true }
            )
        }

        // WHEN
        composeTestRule.onNodeWithContentDescription("Refresh").performClick()

        // THEN
        assert(clicked)
    }
}
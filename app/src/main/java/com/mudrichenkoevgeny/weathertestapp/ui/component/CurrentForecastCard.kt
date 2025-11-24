package com.mudrichenkoevgeny.weathertestapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.mudrichenkoevgeny.weathertestapp.R
import com.mudrichenkoevgeny.weathertestapp.domain.model.Forecast
import com.mudrichenkoevgeny.weathertestapp.domain.model.mock.ForecastMock

@Composable
fun CurrentForecastCard(
    locationName: String,
    forecast: Forecast,
    onRefreshClicked:() -> Unit
) {
    CurrentForecastCardUI(
        locationName = locationName,
        temperature = forecast.temperature,
        windSpeed = forecast.windSpeed,
        precipitation = forecast.precipitation,
        conditionText = forecast.conditionText,
        conditionIconUrl = forecast.conditionIconUrl,
        onRefreshClicked = onRefreshClicked
    )
}

@Composable
private fun CurrentForecastCardUI(
    locationName: String,
    temperature: Double,
    windSpeed: Double,
    precipitation: Double,
    conditionText: String,
    conditionIconUrl: String,
    onRefreshClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {

        IconButton(
            onClick = onRefreshClicked,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.refresh),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(conditionIconUrl)
                    .build(),
                contentDescription = conditionText,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = locationName,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = stringResource(
                        R.string.forecast_temperature_short,
                        temperature
                    ),
                    style = MaterialTheme.typography.displaySmall
                )

                Text(
                    text = conditionText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.forecast_wind_title, windSpeed),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = stringResource(R.string.forecast_precipitation_title, precipitation),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentForecastCardPreview() {
    val forecast = ForecastMock.getForecast()
    CurrentForecastCardUI(
        locationName = ForecastMock.LOCATION_NAME,
        temperature = forecast.temperature,
        windSpeed = forecast.windSpeed,
        precipitation = forecast.precipitation,
        conditionText = forecast.conditionText,
        conditionIconUrl = forecast.conditionIconUrl,
        onRefreshClicked = { }
    )
}
package com.mudrichenkoevgeny.weathertestapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.mudrichenkoevgeny.weathertestapp.R
import com.mudrichenkoevgeny.weathertestapp.core.utils.toDayShortText
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastDay
import com.mudrichenkoevgeny.weathertestapp.domain.model.mock.ForecastMock

@Composable
fun DaysForecastCard(
    daysForecast: List<ForecastDay>
) {
    DaysForecastCardUI(
        daysForecast = daysForecast
    )
}

@Composable
fun DaysForecastCardUI(
    daysForecast: List<ForecastDay>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(R.string.forecast_daily),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(daysForecast) { day ->
                DayForecastItem(day)
            }
        }
    }
}

@Composable
private fun DayForecastItem(day: ForecastDay) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .width(90.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(day.forecast.conditionIconUrl)
                .build(),
            contentDescription = day.forecast.conditionText,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(
                R.string.forecast_temperature_short,
                day.forecast.temperature
            ),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = day.forecast.conditionText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.heightIn(min = 48.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(
                R.string.forecast_wind_short,
                day.forecast.windSpeed
            ),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = stringResource(
                R.string.forecast_precipitation_short,
                day.forecast.precipitation
            ),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = day.dateEpoch.toDayShortText(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DaysForecastCardPreview() {
    val daysForecast = ForecastMock.getDaysForecast()
    DaysForecastCardUI(
        daysForecast = daysForecast
    )
}
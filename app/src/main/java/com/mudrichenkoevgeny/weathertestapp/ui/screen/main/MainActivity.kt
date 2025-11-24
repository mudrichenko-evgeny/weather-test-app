package com.mudrichenkoevgeny.weathertestapp.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast.ForecastScreen
import com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast.ForecastViewModel
import com.mudrichenkoevgeny.weathertestapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(useDarkTheme = isSystemInDarkTheme()) {
                ForecastScreen(viewModel)
            }
        }
    }
}
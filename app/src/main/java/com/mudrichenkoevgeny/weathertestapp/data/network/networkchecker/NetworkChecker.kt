package com.mudrichenkoevgeny.weathertestapp.data.network.networkchecker

interface NetworkChecker {
    fun isInternetAvailable(): Boolean
}
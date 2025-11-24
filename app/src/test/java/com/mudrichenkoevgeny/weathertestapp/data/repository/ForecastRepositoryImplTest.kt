package com.mudrichenkoevgeny.weathertestapp.data.repository

import com.mudrichenkoevgeny.weathertestapp.data.exception.NoInternetException
import com.mudrichenkoevgeny.weathertestapp.data.mapper.toDomain
import com.mudrichenkoevgeny.weathertestapp.data.network.model.CurrentDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.ForecastDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.LocationDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.response.ForecastResponseDto
import com.mudrichenkoevgeny.weathertestapp.data.network.model.stubs.createForecastResponseDtoStub
import com.mudrichenkoevgeny.weathertestapp.data.network.networkchecker.NetworkChecker
import com.mudrichenkoevgeny.weathertestapp.data.network.restapi.ForecastRestApi
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastRepositoryImplTest {

    private val networkChecker = mockk<NetworkChecker>()
    private val forecastRestApi = mockk<ForecastRestApi>()

    private val repository = ForecastRepositoryImpl(networkChecker, forecastRestApi)

    @Test
    fun `getForecast returns failure when no internet`() = runTest {
        // GIVEN
        coEvery { networkChecker.isInternetAvailable() } returns false

        // WHEN
        val result = repository.getForecast()

        // THEN
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NoInternetException)
    }

    @Test
    fun `getForecast returns success when API returns data`() = runTest {
        // GIVEN
        coEvery { networkChecker.isInternetAvailable() } returns true
        val dto = createForecastResponseDtoStub()
        coEvery { forecastRestApi.getForecast(any(), any(), any(), any()) } returns dto

        val expected = dto.toDomain()

        // WHEN
        val result = repository.getForecast()

        // THEN
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `getForecast returns failure when API throws exception`() = runTest {
        // GIVEN
        coEvery { networkChecker.isInternetAvailable() } returns true
        val exception = RuntimeException("API error")
        coEvery { forecastRestApi.getForecast(any(), any(), any(), any()) } throws exception

        // WHEN
        val result = repository.getForecast()

        // THEN
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
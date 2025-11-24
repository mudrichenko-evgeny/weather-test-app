package com.mudrichenkoevgeny.weathertestapp.ui.screen.forecast

import com.mudrichenkoevgeny.weathertestapp.domain.error.AppError
import com.mudrichenkoevgeny.weathertestapp.domain.model.ForecastData
import com.mudrichenkoevgeny.weathertestapp.domain.model.mock.ForecastMock
import com.mudrichenkoevgeny.weathertestapp.domain.repository.ForecastRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: ForecastRepository
    private lateinit var viewModel: ForecastViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = ForecastViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadForecast_success_updates_uiState_content() = runTest {
        val now = System.currentTimeMillis() / 1000

        val mockData = mockk<ForecastData>()
        every { mockData.locationName } returns "Moscow"
        every { mockData.current } returns ForecastMock.getForecast()
        every { mockData.getTodayForecast() } returns ForecastMock.getTodayForecast()
        every { mockData.forecastDayList } returns ForecastMock.getDaysForecast()

        coEvery { repository.getForecast() } returns Result.success(mockData)

        viewModel.loadForecast()
        advanceUntilIdle()

        val state = viewModel.uiState.value as ForecastUiState.Content
        assert(state.locationName == "Moscow")
        assert(state.currentForecast == mockData.current)
        assert(state.daysForecast.size == mockData.forecastDayList.size)

        state.todayForecast.forEach {
            assert(it.timeEpoch >= now)
        }
    }

    @Test
    fun loadForecast_error_emits_event_and_sets_error_state() = runTest {
        // GIVEN
        val exception = RuntimeException("boom")
        coEvery { repository.getForecast() } returns Result.failure(exception)

        val events = mutableListOf<ForecastUiEvent>()
        val job = launch {
            viewModel.uiEvent.collect { events += it }
        }

        // WHEN
        viewModel.loadForecast()
        advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value
        assert(state is ForecastUiState.Error)
        assert((state as ForecastUiState.Error).appError is AppError.Unknown)

        assert(events.isNotEmpty())
        val event = events.first()
        assert(event is ForecastUiEvent.ShowErrorDialog)
        assert((event as ForecastUiEvent.ShowErrorDialog).appError is AppError.Unknown)

        job.cancel()
    }

    @Test
    fun loadForecast_sets_loading_before_result() = runTest {
        // GIVEN
        coEvery { repository.getForecast() } coAnswers {
            delay(100)
            Result.success(mockk(relaxed = true))
        }

        val collect = mutableListOf<ForecastUiState>()
        val job = launch { viewModel.uiState.collect { collect += it } }

        // WHEN
        viewModel.loadForecast()
        advanceUntilIdle()

        // THEN
        assert(collect.first() is ForecastUiState.Loading)
        assert(collect.any { it is ForecastUiState.Content })

        job.cancel()
    }
}

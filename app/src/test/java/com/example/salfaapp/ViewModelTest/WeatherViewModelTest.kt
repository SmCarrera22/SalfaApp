package com.example.salfaapp.ViewModelTest

import com.example.salfaapp.network.api.remote.Main
import com.example.salfaapp.network.api.remote.Weather
import com.example.salfaapp.network.api.remote.WeatherResponse
import com.example.salfaapp.network.repository.WeatherRepository
import com.example.salfaapp.ui.weather.WeatherViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private val repository: WeatherRepository = mockk()
    private lateinit var viewModel: WeatherViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchWeather success updates weather state`() = runTest {
        val fakeWeather = WeatherResponse(
            name = "Santiago",
            main = Main(
                temp = 20f,
                feels_like = 19f,
                temp_min = 18f,
                temp_max = 22f,
                humidity = 50
            ),
            weather = listOf(
                Weather(
                    main = "Clear",
                    description = "Soleado",
                    icon = "01d"
                )
            )
        )

        coEvery { repository.getWeather(10.0, 20.0) } returns fakeWeather

        viewModel.fetchWeather(10.0, 20.0)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(fakeWeather, viewModel.weather.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `fetchWeather failure keeps weather null`() = runTest {
        coEvery { repository.getWeather(any(), any()) } throws Exception("API error")

        viewModel.fetchWeather(0.0, 0.0)
        testDispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.weather.value)
        assertFalse(viewModel.isLoading.value)
    }
}
package com.example.salfaapp.network.repository

import com.example.salfaapp.network.api.remote.WeatherApi

class WeatherRepository(
    private val api: WeatherApi,
    private val apiKey: String
) {
    suspend fun getWeather(lat: Double, lon: Double) =
        api.getWeather(lat, lon, apiKey)
}
package com.example.salfaapp.network

import com.example.salfaapp.network.api.remote.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitWeatherClient {

    private const val BASE_URL = "https://api.openweathermap.org/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi: WeatherApi by lazy {
        instance.create(WeatherApi::class.java)
    }
}
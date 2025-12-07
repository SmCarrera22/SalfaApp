package com.example.salfaapp.network.api.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: Main,
    @SerializedName("name") val name: String
)

data class Weather(
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class Main(
    @SerializedName("temp") val temp: Float,
    @SerializedName("feels_like") val feels_like: Float,
    @SerializedName("temp_min") val temp_min: Float,
    @SerializedName("temp_max") val temp_max: Float,
    @SerializedName("humidity") val humidity: Int
)
package com.eventplanner.model

data class WeatherModel(

    val main: Main,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

data class Wind(
    val speed: Double
)

data class Weather(
    val description: String,
    val icon: String,
    val main: String
)

data class Main(
    val temp: Double

)
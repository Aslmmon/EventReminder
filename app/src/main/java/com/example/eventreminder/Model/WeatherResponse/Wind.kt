package com.example.eventreminder.Model.WeatherResponse


import com.squareup.moshi.Json

data class Wind(
    @Json(name = "speed")
    val speed: Double
)
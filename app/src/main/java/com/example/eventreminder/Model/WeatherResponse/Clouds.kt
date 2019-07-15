package com.example.eventreminder.Model.WeatherResponse


import com.squareup.moshi.Json

data class Clouds(
    @Json(name = "all")
    val all: Int
)
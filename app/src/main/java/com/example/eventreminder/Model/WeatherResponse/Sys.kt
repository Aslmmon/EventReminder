package com.example.eventreminder.Model.WeatherResponse


import com.squareup.moshi.Json

data class Sys(
    @Json(name = "country")
    val country: String
)
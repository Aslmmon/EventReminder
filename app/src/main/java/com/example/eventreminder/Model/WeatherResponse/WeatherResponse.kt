package com.example.eventreminder.Model.WeatherResponse


import com.squareup.moshi.Json

data class WeatherResponse(
    @Json(name = "cod")
    val cod: String,
    @Json(name = "count")
    val count: Int,
    @Json(name = "list")
    val list: List<X>,
    @Json(name = "message")
    val message: String
)
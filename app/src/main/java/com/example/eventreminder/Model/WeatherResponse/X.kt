package com.example.eventreminder.Model.WeatherResponse


import com.squareup.moshi.Json

data class X(
    @Json(name = "clouds")
    val clouds: Clouds,
    @Json(name = "coord")
    val coord: Coord,
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    val main: Main,
    @Json(name = "name")
    val name: String,
    @Json(name = "sys")
    val sys: Sys,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "wind")
    val wind: Wind
)
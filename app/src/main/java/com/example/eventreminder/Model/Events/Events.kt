package com.example.eventreminder.Model.Events


import com.squareup.moshi.Json

data class Events(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "paging")
    val paging: Paging
)
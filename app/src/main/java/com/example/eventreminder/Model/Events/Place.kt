package com.example.eventreminder.Model.Events


import com.squareup.moshi.Json

data class Place(
//    @Json(name = "id")
//    val id: String,
//    @Json(name = "location")
//    val location: Location,
    @Json(name = "name")
    val name: String
)
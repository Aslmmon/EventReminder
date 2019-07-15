package com.example.eventreminder.Model.Events


import com.squareup.moshi.Json

data class Cursors(
    @Json(name = "after")
    val after: String,
    @Json(name = "before")
    val before: String
)
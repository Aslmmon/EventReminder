package com.example.eventreminder.Model.Events


import com.squareup.moshi.Json

data class Paging(
    @Json(name = "cursors")
    val cursors: Cursors
)
package com.example.eventreminder.Model.Events


import androidx.annotation.Nullable
import com.squareup.moshi.Json

data class Data(
    @Json(name = "description")
    val description: String,
//    @Json(name = "end_time")
//    @Nullable
//    val endTime: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "place")
    val place: Place,
    @Json(name = "rsvp_status")
    val rsvpStatus: String,
    @Json(name = "start_time")
    val startTime: String
)
package com.example.musicapi.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Songs(
    @Json(name = "resultCount")
    val resultCount: Int?,
    @Json(name = "results")
    val results: List<Result?>?
)
package com.example.musicapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Rock(
    @Json(name = "artistId")
    val artistId: Int?,
    @Json(name = "artistName")
    val artistName: String?,
    @Json(name = "artworkUrl60")
    val artworkUrl60: String?,
    @Json(name = "collectionName")
    val collectionName: String?,
    @Json(name = "previewUrl")
    val previewUrl: String?,
    @Json(name = "trackId")
    val trackId: Int?,
    @Json(name = "trackName")
    val trackName: String?,
    @Json(name = "trackPrice")
    val trackPrice: Double?,
)

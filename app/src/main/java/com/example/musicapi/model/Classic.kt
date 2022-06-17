package com.example.musicapi.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Classic(
    @Json(name = "artistId")
    val artistId: Int?,
    @Json(name = "artistName")
    val artistName: String?,
    @Json(name = "artistViewUrl")
    val artistViewUrl: String?,
    @Json(name = "artworkUrl100")
    val artworkUrl100: String?,
    @Json(name = "artworkUrl30")
    val artworkUrl30: String?,
    @Json(name = "artworkUrl60")
    val artworkUrl60: String?,
    @Json(name = "collectionCensoredName")
    val collectionCensoredName: String?,
    @Json(name = "collectionExplicitness")
    val collectionExplicitness: String?,
    @Json(name = "collectionId")
    val collectionId: Int?,
    @Json(name = "collectionName")
    val collectionName: String?,
    @Json(name = "collectionPrice")
    val collectionPrice: Double?,
    @Json(name = "collectionViewUrl")
    val collectionViewUrl: String?,
    @Json(name = "contentAdvisoryRating")
    val contentAdvisoryRating: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "currency")
    val currency: String?,
    @Json(name = "discCount")
    val discCount: Int?,
    @Json(name = "discNumber")
    val discNumber: Int?,
    @Json(name = "isStreamable")
    val isStreamable: Boolean?,
    @Json(name = "kind")
    val kind: String?,
    @Json(name = "previewUrl")
    val previewUrl: String?,
    @Json(name = "primaryGenreName")
    val primaryGenreName: String?,
    @Json(name = "releaseDate")
    val releaseDate: String?,
    @Json(name = "trackCensoredName")
    val trackCensoredName: String?,
    @Json(name = "trackCount")
    val trackCount: Int?,
    @Json(name = "trackExplicitness")
    val trackExplicitness: String?,
    @Json(name = "trackId")
    val trackId: Int?,
    @Json(name = "trackName")
    val trackName: String?,
    @Json(name = "trackNumber")
    val trackNumber: Int?,
    @Json(name = "trackPrice")
    val trackPrice: Double?,
    @Json(name = "trackTimeMillis")
    val trackTimeMillis: Int?,
    @Json(name = "trackViewUrl")
    val trackViewUrl: String?,
    @Json(name = "wrapperType")
    val wrapperType: String?
)
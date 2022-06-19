package com.example.musicapi.model

import java.io.Serializable

data class SongInfo (

    val artistName: String?,
    val artworkUrl100: String?,
    val trackName: String?,
    val previewUrl: String?

    ): Serializable
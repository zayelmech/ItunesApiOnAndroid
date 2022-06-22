package com.example.musicapi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs_entity")
data class SongsEntity(
/*
    @PrimaryKey @ColumnInfo(name = "trackId") val trackId: Int?,
    @ColumnInfo(name = "artistName") val artistName: String?,
    @ColumnInfo(name = "artworkUrl100") val artworkUrl100: String?,
    @ColumnInfo(name = "artworkUrl60") val artworkUrl60: String?,
    @ColumnInfo(name = "trackName") val trackName: String?,
    @ColumnInfo(name = "previewUrl") val previewUrl: String
*/

    @PrimaryKey val trackId: Int,
    val artistName: String,
    val artworkUrl100: String,
    val artworkUrl60: String,
    val trackName: String,
    val trackPrice: Double,
    val previewUrl: String
)
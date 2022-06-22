package com.example.musicapi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs_entity")
data class SongsEntity(

    @PrimaryKey val trackId: Int,
    val artistName: String,
    val artworkUrl100: String,
    val artworkUrl60: String,
    val trackName: String,
    val trackPrice: Double,
    val previewUrl: String
)
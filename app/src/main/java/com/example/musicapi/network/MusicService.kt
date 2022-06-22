package com.example.musicapi.network
import com.example.musicapi.model.Songs
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MusicService {

    @GET(SINGLE_SONG)
    fun getAllRockSongs(
        @Query("term") term: String = "rock",
        @Query("amp;media") media: String = "music",
        @Query("amp;entity") entity: String = "song",
        @Query("amp;limit") limit: Int = 50
    ) : Single<Songs>
    // fun getAllRockSongs() : Single<List<Rock>>

    @GET(SINGLE_SONG)
    fun getAllClassicSongs(
        @Query("term") term: String = "classick",
        @Query("amp;media") media: String = "music",
        @Query("amp;entity") entity: String = "song",
        @Query("amp;limit") limit: Int = 50
    ) : Single<Songs>

    @GET(SINGLE_SONG)
    fun getAllPopSongs(
        @Query("term") term: String = "pop",
        @Query("amp;media") media: String = "music",
        @Query("amp;entity") entity: String = "song",
        @Query("amp;limit") limit: Int = 50
    ) : Single<Songs>

    @GET(SINGLE_SONG)
    fun getRockSongByName(
        @Query("songName") songName: String,
        @Query("limit") limit: Int = 25
    ) : Single<Songs>

companion object{

    const val BASE_URL = "https://itunes.apple.com/"

    // HERE INSTEAD OF HAVING THE WHOLE PATH WITH QUERIES WE NEED TO USE @Query from retrofit
    private const val SINGLE_SONG = "search"
}
}
package com.example.musicapi.network
import com.example.musicapi.model.Songs
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicService {

    @GET(ROCK_PATH)
    fun getAllRockSongs() : Single<Songs>
    // fun getAllRockSongs() : Single<List<Rock>>

    @GET(CLASSIC_PATH)
    fun getAllClassicSongs() : Single<Songs>

    @GET(POP_PATH)
    fun getAllPopSongs() : Single<Songs>

    @GET(SINGLE_SONG)
    fun getRockSongByName(
        @Path("songName") songName: String
    ) : Single<Songs>

companion object{

    const val BASE_URL = "https://itunes.apple.com/"
    private const val ROCK_PATH = "search?term=rock&amp;media=music&amp;entity=song&amp;limit=5"
    private const val CLASSIC_PATH = "search?term=classick&amp;media=music&amp;entity=song&amp;limit=50"
    private const val POP_PATH = "search?term=pop&amp;media=music&amp;entity=song&amp;limit=50"
    private const val SINGLE_SONG = "search?term={songName}&limit=25. "
}
}
package com.example.musicapi.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SongsDao {
    @Query("SELECT * FROM songs_entity")
    fun getAll(): LiveData<List<SongsEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(songs: SongsEntity)
    //suspend fun insert(vararg songs: SongsEntity)


//    @Query("DELETE FROM songs_entity")
  //   suspend fun deleteAll()

    /**

    @Query("SELECT * FROM songs WHERE trackId IN (:songIds)")
    fun loadAllByIds(songIds: IntArray): List<SongsEntity>

    @Query(
    "SELECT * FROM songs WHERE trackName LIKE :artistName AND " + "artistName LIKE :songName LIMIT 1"
    )
    fun findByName(songName: String, artistName: String): SongsEntity

    @Delete
    fun delete(song: SongsEntity)
     */
}


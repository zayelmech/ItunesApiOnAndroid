package com.example.musicapi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [SongsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun songDao(): SongsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "songs_database"
                )
                    //.fallbackToDestructiveMigration()
                    //.addCallback(SongDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
        /*
        private class SongDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.songDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(songsDao: SongsDao) {
            // Delete all content here.
            //songsDao.deleteAll()
            // Add sample words.
            var word = SongsEntity(0, "Name", "", "", "Song", "")
           // songsDao.insert(word)
            word = SongsEntity(1, "Artista", "", "", "Cancion", "")
           // songsDao.insert(word)
        }*/
    }

}
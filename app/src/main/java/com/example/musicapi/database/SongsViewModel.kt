package com.example.musicapi.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongsViewModel( application: Application) : AndroidViewModel(application) {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val readAllData: LiveData<List<SongsEntity>>
    private val repository : SongsRepository
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    init {
        val songsDao = AppDatabase.getDatabase(application).songDao()
        repository = SongsRepository(songsDao)
        readAllData = repository.readAllData
    }

    fun insert(songEntity: SongsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(songEntity)
        }
    }
}

package com.example.musicapi.presenter

import com.example.musicapi.model.Rock
import com.example.musicapi.model.Songs

interface RockContracts {
    interface RockViewContract {
        fun loadingState()
        fun connectionChecked()
        //fun allSongsLoadedSuccess(songs: List<Rock>)
        fun allSongsLoadedSuccess(songs: Songs)
        fun onError(error: Throwable)
    }

    interface RockPresenterContract {
       fun initializePresenter(viewContract : RockViewContract)
        fun registerForNetworkState()
        fun getAllSongs()
        fun destroyPresenter()
    }
}
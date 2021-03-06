package com.example.musicapi.presenter

import com.example.musicapi.model.Songs

interface RockContracts {
    interface RockViewContract {
        fun loadingState()
       fun connectionChecked(networkState : Boolean)
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
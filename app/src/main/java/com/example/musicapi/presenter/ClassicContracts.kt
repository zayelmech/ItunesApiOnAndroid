package com.example.musicapi.presenter

import com.example.musicapi.model.Songs

interface ClassicContracts {
    interface ClassicViewContract {
        fun loadingState()
        fun connectionChecked(networkState : Boolean)
        fun allSongsLoadedSuccess(songs: Songs)
        fun onError(error: Throwable)
    }

    interface ClassicPresenterContract {
        fun initializePresenter(viewContract : ClassicViewContract)
        fun registerForNetworkState()
        fun getAllSongs()
        fun destroyPresenter()
    }
}
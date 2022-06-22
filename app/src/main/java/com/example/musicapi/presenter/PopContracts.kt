package com.example.musicapi.presenter

import com.example.musicapi.model.Songs

interface PopContracts {
    interface PopViewContract {
        fun loadingState()
        fun connectionChecked(networkState : Boolean)
        fun allSongsLoadedSuccess(songs: Songs)
        fun onError(error: Throwable)
    }

    interface PopPresenterContract {
        fun initializePresenter(viewContract : PopViewContract)
        fun registerForNetworkState()
        fun getAllSongs()
        fun destroyPresenter()
    }
}
package com.example.musicapi.presenter

import android.net.ConnectivityManager
import com.example.musicapi.network.MusicRepository
import com.example.musicapi.network.MusicRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PopPresenter(
    private val connectivityManager: ConnectivityManager? = null,
    private val popRepository: MusicRepository = MusicRepositoryImpl(connectivityManager),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
):PopContracts.PopPresenterContract {

    private var popViewContract: PopContracts.PopViewContract? = null

    override fun initializePresenter(viewContract: PopContracts.PopViewContract) {
        popViewContract= viewContract
    }

    override fun registerForNetworkState() {
        popRepository.checkNetworkAvailability()
    }

    override fun getAllSongs() {
        compositeDisposable.clear()
        popViewContract?.loadingState()

        popRepository.networkState
            .subscribe { netState ->

                popViewContract?.connectionChecked(netState)
                if (netState) {
                    popRepository.getAllPopSongs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                //Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", it.results.toString())
                                //here you are in the main thread  updating using the reference for the contract
                                popViewContract?.allSongsLoadedSuccess(it)
                            },
                            {
                                //here you are in the main thread  updating using the reference for the contract
                                popViewContract?.onError(it)
                            }
                        ).also { compositeDisposable.add(it) }
                } else {
                    //here you are in the main thread  updating using the reference for the contract
                    //this is the error when you do not have internet connection
                    popViewContract?.onError(Throwable("NO INTERNET CONNECTION"))
                }
            }
            .also { compositeDisposable.add(it) }
    }

    override fun destroyPresenter() {
        popViewContract =null
        compositeDisposable.clear()
    }
}
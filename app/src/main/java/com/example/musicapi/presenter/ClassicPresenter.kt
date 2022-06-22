package com.example.musicapi.presenter

import android.net.ConnectivityManager
import com.example.musicapi.network.MusicRepository
import com.example.musicapi.network.MusicRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ClassicPresenter(
    private val connectivityManager: ConnectivityManager? = null,
    private val classicRepository: MusicRepository = MusicRepositoryImpl(connectivityManager),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : ClassicContracts.ClassicPresenterContract {

    private var classicViewContract: ClassicContracts.ClassicViewContract? = null

    override fun initializePresenter(viewContract: ClassicContracts.ClassicViewContract) {
        classicViewContract = viewContract
    }

    override fun registerForNetworkState() {
        classicRepository.checkNetworkAvailability()
    }

    override fun getAllSongs() {
        compositeDisposable.clear()
        classicViewContract?.loadingState()

        classicRepository.networkState
            .subscribe { netState ->

                classicViewContract?.connectionChecked(netState)
                if (netState) {
                    classicRepository.getAllClassicSongs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                //Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", it.results.toString())
                                //here you are in the main thread  updating using the reference for the contract
                                classicViewContract?.allSongsLoadedSuccess(it)
                            },
                            {
                                //here you are in the main thread  updating using the reference for the contract
                                classicViewContract?.onError(it)
                            }
                        ).also { compositeDisposable.add(it) }
                } else {
                    //here you are in the main thread  updating using the reference for the contract
                    //this is the error when you do not have internet connection
                    classicViewContract?.onError(Throwable("NO INTERNET CONNECTION"))
                }
            }
            .also { compositeDisposable.add(it) }
    }

    override fun destroyPresenter() {
        classicViewContract = null
        compositeDisposable.clear()
    }
}


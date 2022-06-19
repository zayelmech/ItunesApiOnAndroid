package com.example.musicapi.presenter

import android.net.ConnectivityManager
import android.util.Log
import com.example.musicapi.network.MusicRepository
import com.example.musicapi.network.MusicRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class RockPresenter(
    private val connectivityManager: ConnectivityManager? = null,
    private val rockRepository: MusicRepository = MusicRepositoryImpl(connectivityManager),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : RockContracts.RockPresenterContract {

    private var rockViewContract: RockContracts.RockViewContract? = null

    /**
     * This will initialize your presenter, pasing the reference for the view contract interface
     * */
    override fun initializePresenter(viewContract: RockContracts.RockViewContract) {
        rockViewContract = viewContract
    }

    /**
     * This will register for network state updates
     * */

    override fun registerForNetworkState() {
        rockRepository.checkNetworkAvailability()
    }

    /**
     * This will retrieve the data from the server if the network is available
     *
     *You first subscribe to the network state and you perform the service API call
     *
     * When it goes to success, we call the view contract interface to update our UI
     *
     * When it goes to error, we call the view contract interface to update our UI
     * */

    override fun getAllSongs() {
        rockViewContract?.loadingState()

        rockRepository.networkState
            .subscribe { netState ->
                if (netState) {
                    rockRepository.getAllRockSongs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                //Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", it.results.toString())

                                //here you are in the main thread  updating using the reference for the contract
                                rockViewContract?.allSongsLoadedSuccess(it)
                            },
                            {
                                //here you are in the main thread  updating using the reference for the contract
                                rockViewContract?.onError(it)
                            }
                        ).also { compositeDisposable.add(it) }
                } else {
                    //here you are in the main thread  updating using the reference for the contract
                    //this is the error when you do not have internet connection
                    rockViewContract?.onError(Throwable("NO INTERNET CONNECTION"))
                }
            }
            .also { compositeDisposable.add(it) }
    }
    /**
     * This method is mandatory to avoid memory leaks
     * */
    override fun destroyPresenter() {
        rockViewContract = null
        //compositeDisposable.dispose()
        compositeDisposable.clear()

    }
}


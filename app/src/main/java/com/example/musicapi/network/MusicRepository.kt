package com.example.musicapi.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.*
import android.net.NetworkRequest
import com.example.musicapi.model.Songs
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

interface MusicRepository {
    val networkState : BehaviorSubject<Boolean>
    fun checkNetworkAvailability()
    fun getAllRockSongs(): Single<Songs>
    fun getAllClassicSongs(): Single<Songs>
    fun getAllPopSongs(): Single<Songs>
    fun getRockSongByName(songName: String): Single<Songs>

}

class MusicRepositoryImpl(
    private val connectivityManager: ConnectivityManager?,
    private val networkRequest: NetworkRequest =NetworkRequest.Builder()
        .addCapability(NET_CAPABILITY_INTERNET)
        //.addTransportType(TRANSPORT_WIFI)
        .addTransportType(TRANSPORT_CELLULAR)
        .build()

) : MusicRepository {

    override  val networkState : BehaviorSubject<Boolean> = BehaviorSubject.create()

    override fun checkNetworkAvailability() {

      connectivityManager?.requestNetwork(networkRequest,object :
      ConnectivityManager.NetworkCallback(){
          override fun onAvailable(network: Network) {
              super.onAvailable(network)
              networkState.onNext(true)
          }

          override fun onUnavailable() {
              super.onUnavailable()
              networkState.onNext(false)
          }

          override fun onLost(network: Network) {
              super.onLost(network)
              networkState.onNext(false)
          }
      })
    }

    override fun getAllRockSongs(): Single<Songs> =
        Service.musicService.getAllRockSongs()


    override fun getAllClassicSongs(): Single<Songs> =
        Service.musicService.getAllClassicSongs()

    override fun getAllPopSongs(): Single<Songs> =
        Service.musicService.getAllPopSongs()


    override fun getRockSongByName(songName: String): Single<Songs> =
        Service.musicService.getRockSongByName(songName)

    private fun checkCapabilities(): NetworkCapabilities? {
        connectivityManager?.activeNetwork?.let {
            return connectivityManager.getNetworkCapabilities(it)
        } ?: return null
    }
}
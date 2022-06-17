package com.example.musicapi.view

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapi.adapter.MusicAdapter
import com.example.musicapi.adapter.MusicItemClick
import com.example.musicapi.databinding.FragmentRockBinding
import com.example.musicapi.model.Result
import com.example.musicapi.model.Songs
import com.example.musicapi.presenter.RockContracts
import com.example.musicapi.presenter.RockPresenter


class RockFragment : Fragment(), RockContracts.RockViewContract {

    private val binding by lazy {
        FragmentRockBinding.inflate(layoutInflater)
    }

    private val rockPresenter: RockContracts.RockPresenterContract by lazy {

        RockPresenter(
            connectivityManager = getSystemService(
                requireContext(),
                ConnectivityManager::class.java
            )
        )
    }

    /*private val compositeDisposable by lazy {
        CompositeDisposable()
    }*/

    private val rockAdapter by lazy {
        MusicAdapter(object : MusicItemClick {
            override fun onSongClicked(song: Result) {
                Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "${song.trackName}")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "onCreateView")

        binding.rockRecycleView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = rockAdapter
        }
        rockPresenter.initializePresenter(this)
        rockPresenter.registerForNetworkState()
        rockPresenter.getAllSongs()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rockPresenter.destroyPresenter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }


    override fun loadingState() {
        Toast.makeText(requireContext(),"LOADING ...",Toast.LENGTH_LONG).show()
    }

    override fun connectionChecked() {
        // NO-OP
   }

//    override fun allSongsLoadedSuccess(songs: List<Rock>) {
//        rockAdapter.updateSongs(songs)
//    }
        override fun allSongsLoadedSuccess(songs: Songs) {
    Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", songs.resultCount?.toString() +  " ---NUMBER")
    rockAdapter.updateSongs(songs)
    }

    override fun onError(error: Throwable) {
        Toast.makeText(requireContext(),"ERROR!! 404" ,Toast.LENGTH_LONG).show()
        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", error.toString())

    }
}
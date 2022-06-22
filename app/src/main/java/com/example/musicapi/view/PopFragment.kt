package com.example.musicapi.view

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapi.R
import com.example.musicapi.adapter.MusicAdapter
import com.example.musicapi.adapter.MusicItemClick
import com.example.musicapi.database.SongsViewModel
import com.example.musicapi.databinding.FragmentPopBinding
import com.example.musicapi.model.Result
import com.example.musicapi.model.Songs
import com.example.musicapi.presenter.ClassicContracts
import com.example.musicapi.presenter.ClassicPresenter
import com.example.musicapi.presenter.PopContracts
import com.example.musicapi.presenter.PopPresenter


class PopFragment : Fragment(),PopContracts.PopViewContract {

    private val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
    }

    private val popPresenter: PopContracts.PopPresenterContract by lazy {
       PopPresenter(
            connectivityManager = ContextCompat.getSystemService(
                requireContext(),
                ConnectivityManager::class.java
            )
        )
    }
    private val listAdapter by lazy {
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

        binding.recyclerviewPop.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }

        popPresenter.initializePresenter(this)
        popPresenter.registerForNetworkState()
        popPresenter.getAllSongs()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        popPresenter.destroyPresenter()
    }

    override fun loadingState() {
        Toast.makeText(requireContext(), "LOADING ...", Toast.LENGTH_LONG).show()

    }

    override fun connectionChecked(networkState: Boolean) {
        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "${networkState.toString()} ---HERE")
    }

    override fun allSongsLoadedSuccess(songs: Songs) {
        Toast.makeText(requireContext(), "LOADED", Toast.LENGTH_LONG).show()
        listAdapter.updateSongs(songs)

    }

    override fun onError(error: Throwable) {
        Toast.makeText(requireContext(), "ERROR!! 404", Toast.LENGTH_LONG).show()
    }


}
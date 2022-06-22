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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapi.adapter.MusicAdapter
import com.example.musicapi.adapter.MusicItemClick
import com.example.musicapi.database.SongsEntity
import com.example.musicapi.database.SongsViewModel
import com.example.musicapi.databinding.FragmentClassicBinding
import com.example.musicapi.model.Result
import com.example.musicapi.model.Songs
import com.example.musicapi.presenter.ClassicContracts
import com.example.musicapi.presenter.ClassicPresenter


class ClassicFragment : Fragment(), ClassicContracts.ClassicViewContract {


    private val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }

    private lateinit var songsViewModel: SongsViewModel

    private val classicPresenter: ClassicContracts.ClassicPresenterContract by lazy {
        ClassicPresenter(
            connectivityManager = ContextCompat.getSystemService(
                requireContext(),
                ConnectivityManager::class.java
            )
        )
    }

    private val listAdapter by lazy {
        MusicAdapter(object : MusicItemClick {
            override fun onSongClicked(song: Result) {
                /**
                val songInfo =
                SongInfo(song.artistName, song.artworkUrl100, song.trackName, song.previewUrl)
                findNavController().navigate(
                R.id.action_rockFragment_to_detailsFragment, bundleOf(
                Pair(DetailsFragment.DATA_CLASS, songInfo)
                )
                )*/
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

        songsViewModel = ViewModelProvider(this)[SongsViewModel::class.java]


        binding.recyclerviewClassic.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }

        /*
        binding.root.setOnRefreshListener {

            rockPresenter.registerForNetworkState()
            rockPresenter.getAllSongs()
            Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "Update Button was clicked")
        }*/

        songsViewModel.readAllData.observe(viewLifecycleOwner, Observer { song ->
            //Update the cached copy of the words in the adapter.
            listAdapter.setLocalData(song)
        })

        classicPresenter.initializePresenter(this)
        classicPresenter.registerForNetworkState()
        classicPresenter.getAllSongs()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //binding.root.isRefreshing =false
        classicPresenter.destroyPresenter()
    }

    override fun loadingState() {
        Toast.makeText(requireContext(), "LOADING ...", Toast.LENGTH_LONG).show()
    }

    override fun connectionChecked(networkState: Boolean) {
        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "${networkState.toString()} ---HERE")
    }

    override fun allSongsLoadedSuccess(songs: Songs) {
        Toast.makeText(requireContext(), "LOADED", Toast.LENGTH_LONG).show()

        Log.d(
            "CLASS::${javaClass.simpleName} MESSAGE ->",
            songs.resultCount?.toString() + " ---NUMBER"
        )
/*
        songs.results?.forEach {
            songsViewModel.insert(
                SongsEntity(
                    it?.trackId!!,
                    it.artistName!!,
                    it.artworkUrl100!!,
                    it.artworkUrl60!!,
                    it.trackName!!,
                    it.trackPrice!!,
                    it.previewUrl!!
                )
            )
        }
        */
        listAdapter.updateSongs(songs)
        //binding.root.isRefreshing = false
    }

    override fun onError(error: Throwable) {
        Toast.makeText(requireContext(), "ERROR!! 404", Toast.LENGTH_LONG).show()
    }

}
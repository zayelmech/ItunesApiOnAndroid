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
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapi.R
import com.example.musicapi.adapter.MusicAdapter
import com.example.musicapi.adapter.MusicItemClick
import com.example.musicapi.database.SongsEntity
import com.example.musicapi.database.SongsViewModel
import com.example.musicapi.model.Result
import com.example.musicapi.model.Songs
import com.example.musicapi.presenter.RockContracts
import com.example.musicapi.presenter.RockPresenter
import com.example.musicapi.databinding.FragmentRockBinding
import com.example.musicapi.model.SongInfo

class RockFragment : Fragment(), RockContracts.RockViewContract {

    private val binding by lazy {
        FragmentRockBinding.inflate(layoutInflater)
    }
    private lateinit var songsViewModel: SongsViewModel

    /**
    private val localDatabaseAdapter by lazy {
        SongsAdapter()
    }
    */
    private val rockPresenter: RockContracts.RockPresenterContract by lazy {
        RockPresenter(
            connectivityManager = getSystemService(
                requireContext(),
                ConnectivityManager::class.java
            )
        )
    }

    private val apiJsonAdapter by lazy {
        MusicAdapter(object : MusicItemClick {
            override fun onSongClicked(song: Result) {
                val songInfo =
                    SongInfo(song.artistName, song.artworkUrl100, song.trackName, song.previewUrl)
                findNavController().navigate(
                    R.id.action_rockFragment_to_detailsFragment, bundleOf(
                        Pair(DetailsFragment.DATA_CLASS, songInfo)
                    )
                )
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

        binding.rockRecycleView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = apiJsonAdapter
        }

        binding.root.setOnRefreshListener {

            rockPresenter.registerForNetworkState()
            rockPresenter.getAllSongs()
            Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "Update Button was clicked")
        }


        songsViewModel.readAllData.observe(viewLifecycleOwner, Observer { song ->
            //Update the cached copy of the words in the adapter.
            //localDatabaseAdapter.setData(song)
            apiJsonAdapter.setLocalData(song)
            //localDatabaseAdapter.notifyDataSetChanged()
        })

        rockPresenter.initializePresenter(this)
        rockPresenter.registerForNetworkState()
        rockPresenter.getAllSongs()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.isRefreshing = false
        rockPresenter.destroyPresenter()
    }

    override fun loadingState() {
        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "LOADING ...")
        Toast.makeText(requireContext(), "LOADING ...", Toast.LENGTH_LONG).show()
    }

    override fun connectionChecked(networkState: Boolean) {
        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "${networkState.toString()} ---HERE")
        // Toast.makeText(requireContext(), networkState.value.toString(), Toast.LENGTH_LONG).show()
    }

    override fun allSongsLoadedSuccess(songs: Songs) {
        Toast.makeText(requireContext(), "LOADED", Toast.LENGTH_LONG).show()

        Log.d(
            "CLASS::${javaClass.simpleName} MESSAGE ->",
            songs.resultCount?.toString() + " ---NUMBER"
        )

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
        apiJsonAdapter.updateSongs(songs)
        binding.root.isRefreshing = false

    }

    override fun onError(error: Throwable) {
        Toast.makeText(requireContext(), "ERROR!! 404", Toast.LENGTH_LONG).show()
        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", error.toString())
        binding.root.isRefreshing = false

    }
}
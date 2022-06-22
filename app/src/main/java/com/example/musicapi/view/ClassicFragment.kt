package com.example.musicapi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager

import com.example.musicapi.database.SongsAdapter

import com.example.musicapi.database.SongsEntity
import com.example.musicapi.database.SongsViewModel
import com.example.musicapi.databinding.FragmentClassicBinding


class ClassicFragment : Fragment() {


    private val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }

    private lateinit var songsViewModel: SongsViewModel

    private val classicAdapter by lazy {
        SongsAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
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
            adapter = classicAdapter
        }


         songsViewModel.readAllData.observe(viewLifecycleOwner,Observer{song ->
      //Update the cached copy of the words in the adapter.
           classicAdapter.setData(song)
         })

        binding.fab.setOnClickListener {
         // songsViewModel.insert(SongsEntity(10,"Hola","HTML","s","Nombre por",12.5,"no"))
            Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", "click on plus")

        }

        return binding.root
    }

}
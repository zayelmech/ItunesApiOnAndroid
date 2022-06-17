package com.example.musicapi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapi.databinding.SongItemBinding
import com.example.musicapi.model.Result
import com.example.musicapi.model.Songs

class MusicAdapter(

    private val onSongItemClick : MusicItemClick,
    private val songsDataSet: MutableList<Result> = mutableListOf()
) : RecyclerView.Adapter<MusicViewHolder>() {

    fun updateSongs(newSongs : Songs){
        newSongs.results?.forEach{

           it?.let { it1 -> songsDataSet.add(it1) }

            Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", it?.trackName.toString())
        }

        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", newSongs.resultCount.toString())
        songsDataSet.forEach{
            Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", it.trackName.toString())
        }

        //songsDataSet.addAll(newSongs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(
            SongItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) =
        holder.bind(songsDataSet[position],onSongItemClick)

    override fun getItemCount(): Int = songsDataSet.size
}

class MusicViewHolder(
    private val binding: SongItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(song: Result, onSongItemClick: MusicItemClick) {
        binding.artistName.text = song.artistName
        binding.collectionName.text = song.collectionName
        binding.trackPrice.text = song.trackPrice.toString()

        itemView.setOnClickListener{
            onSongItemClick.onSongClicked(song)
        }
        Glide.with(binding.root)
            .load(song.artworkUrl60)
            .into(binding.songImage)


    }
}

interface MusicItemClick {
    fun onSongClicked(song: Result)
}
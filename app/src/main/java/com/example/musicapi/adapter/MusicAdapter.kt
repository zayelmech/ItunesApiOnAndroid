package com.example.musicapi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapi.database.SongsEntity
import com.example.musicapi.databinding.SongItemBinding
import com.example.musicapi.model.Result
import com.example.musicapi.model.Songs


class MusicAdapter(
    private val onSongItemClick : MusicItemClick,
    private val songsDataSet : MutableList<Result> = mutableListOf(),
) : RecyclerView.Adapter<MusicViewHolder>() {

    fun updateSongs(newSongs : Songs){
        songsDataSet.clear()
        newSongs.results?.forEach{
           it?.let { it1 ->
               songsDataSet.add(it1)
           }
           // Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", it?.trackName.toString())
        }

        //Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", newSongs.resultCount.toString())
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


    override fun getItemCount(): Int =
        songsDataSet.size

    fun setLocalData(song: List<SongsEntity>) {
        songsDataSet.clear()

        song.forEach{
            it.let { it1 ->
                val newSong = Result(
                    artistName = it1.artistName,
                    artworkUrl100 = it1.artworkUrl100,
                    artworkUrl60 = it1.artworkUrl60,
                    trackId = it1.trackId,
                    trackName = it1.trackName,
                    previewUrl = it1.previewUrl,
                    trackPrice = it1.trackPrice,
                )
                songsDataSet.add(newSong)
            }
            // Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", it?.trackName.toString())
        }
        //Log.d("CLASS::${javaClass.simpleName} MESSAGE ->", newSongs.resultCount.toString())
        //songsDataSet.addAll(newSongs)
        notifyDataSetChanged()
    }
}

class MusicViewHolder(
    private val binding: SongItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(song: Result, onSongItemClick: MusicItemClick ) {

        binding.artistName.text = song.artistName
        binding.collectionName.text = song.trackName
        binding.trackPrice.text = "$ ${song.trackPrice.toString()} USD"

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
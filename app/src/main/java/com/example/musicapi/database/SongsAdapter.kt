package com.example.musicapi.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.musicapi.databinding.SongItemBinding


class SongsAdapter : RecyclerView.Adapter<ClassicViewHolder>() {

    private var userList = emptyList<SongsEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassicViewHolder {
        return ClassicViewHolder(
            SongItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            //LayoutInflater.from(parent.context).inflate(R.layout.song_item,parent)
        )
    }

    override fun onBindViewHolder(holder: ClassicViewHolder, position: Int) =
        holder.bind(userList[position])

    override fun getItemCount(): Int {
        return userList.size
    }
    fun setData(song : List<SongsEntity>){
        this.userList = song
        notifyDataSetChanged()
    }

}
class ClassicViewHolder(
    private val binding: SongItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(song: SongsEntity) {
        binding.artistName.text = song.artistName
        binding.collectionName.text = song.trackName
        binding.trackPrice.text = "$ ${song.trackPrice.toString()} USD"

        Glide.with(binding.root)
            .load(song.artworkUrl60)
            .into(binding.songImage)
    }
}
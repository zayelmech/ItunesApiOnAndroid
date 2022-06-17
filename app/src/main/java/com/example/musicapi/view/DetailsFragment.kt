package com.example.musicapi.view

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapi.R
import com.example.musicapi.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {


    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }
     private  var URL : String? = null
        private var mediaPlayer: MediaPlayer? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        URL = it.getString(DATA_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        playSong(URL?:"https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/e4/6c/ad/e46cad13-317a-3074-8d0f-a41af0bb2437/mzaf_5207796602846861401.plus.aac.p.m4a")
        binding.playBtn.setOnClickListener {
            startSong()
        }
        binding.pauseBtn.setOnClickListener {
            pause()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null

    }

   private fun playSong(link :String){
         mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(link)
            prepare() // might take long! (for buffering, etc)
        }

    }
    private fun startSong(){
        mediaPlayer?.start()
    }
    private fun pause(){
        mediaPlayer?.pause()
    }
    companion object {
        const val DATA_URL = "URL"
    }
}
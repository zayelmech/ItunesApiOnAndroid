package com.example.musicapi.view

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.example.musicapi.R
import com.example.musicapi.databinding.FragmentDetailsBinding
import com.example.musicapi.model.SongInfo


class DetailsFragment : Fragment() {


    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }
    private var URL: String? = null
    private var mediaPlayer: MediaPlayer? = null
    private var songInfo: SongInfo? = null
    private var flagState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //URL = it.getString(DATA_URL)
            songInfo = it.getSerializable(DATA_CLASS) as SongInfo
            URL = songInfo?.previewUrl

            Glide.with(binding.root)
                .load(songInfo?.artworkUrl100)
                .into(binding.imageView)

            binding.trackName.text = songInfo?.trackName
            binding.artistName.text = songInfo?.artistName

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        playSong(
            URL
                ?: "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/e4/6c/ad/e46cad13-317a-3074-8d0f-a41af0bb2437/mzaf_5207796602846861401.plus.aac.p.m4a"
        )
        binding.playBtn.setOnClickListener {
            startSong()
        }


        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null

    }

    private fun playSong(link: String) {
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
        binding.seekBar.max = mediaPlayer!!.duration
        binding.durationTxt.text = (mediaPlayer!!.duration / 1000).toString()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    binding.seekBar.progress = mediaPlayer!!.currentPosition
                    binding.progressTxt.text = (mediaPlayer!!.currentPosition / 1000).toString()
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    binding.seekBar.progress = 0
                }
            }
        }, 0)

    }

    private fun startSong() {


        flagState = if (flagState) {
            mediaPlayer?.start()
            binding.playBtn.setImageResource(R.drawable.vector_pause)
            false
        } else {
            mediaPlayer?.pause()
            binding.playBtn.setImageResource(R.drawable.vector_play)
            true
        }
    }

    companion object {
        const val DATA_CLASS = "CLASS"
    }
}
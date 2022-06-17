package com.example.musicapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.musicapi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    //private val client :OkHttpClient = OkHttpClient()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
       // run()

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_container
        ) as NavHostFragment
        navController = navHostFragment.navController
        binding.navBarView.setupWithNavController(navController)

    }
    /**
    fun run() {
        val request = Request.Builder()
            .url("https://itunes.apple.com/search?term=rock&amp;media=music&amp;entity=song&amp;limit=50")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        Log.d("CLASS::${javaClass.simpleName} MESSAGE ->","$name: $value")
                     //   println("$name: $value")
                    }

                    println(response.body!!.string())
                }
            }
        })
    }
    */
}
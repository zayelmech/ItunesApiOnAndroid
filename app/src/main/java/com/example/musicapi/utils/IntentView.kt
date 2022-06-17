package com.example.musicapi.utils

sealed class IntentView{
    object AllRock : IntentView()
    object AllClassic : IntentView()
    object AllPop : IntentView()
    data class SongByName(val name: String) : IntentView()
}

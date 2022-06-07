package com.example.myplayeraleja

import com.example.myplayeraleja.data.MediaItem
import com.example.myplayeraleja.data.MediaProvider

class FakeMediaProviderImpl : MediaProvider {
    override fun getItems(): List<MediaItem> = emptyList()

}
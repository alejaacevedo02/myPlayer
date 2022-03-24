package com.example.myplayeraleja

import com.example.myplayeraleja.MediaItem.Type

fun getItems(): List<MediaItem> = (1..10).map {
    MediaItem(
        "Title $it",
        "https://placekitten.com/200/200?image=$it",
        if (it % 3 == 0) Type.VIDEO else Type.PHOTO
    )
}
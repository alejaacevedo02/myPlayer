package com.example.myplayeraleja.data

sealed class Filter {
    object None : Filter()
    class ByType(val type: MediaItem.Type) : Filter()
}

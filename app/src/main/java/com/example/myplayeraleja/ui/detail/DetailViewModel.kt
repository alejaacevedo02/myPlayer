package com.example.myplayeraleja.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myplayeraleja.data.MediaItem
import com.example.myplayeraleja.data.MediaItem.Type
import com.example.myplayeraleja.data.MediaProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val mediaProvider: MediaProvider,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _thumb = MutableLiveData<String>()
    val thumb: LiveData<String> get() = _thumb

    private val _videoIndicatorVisibility = MutableLiveData<Boolean>()
    val videoIndicatorVisibility : LiveData<Boolean> get() = _videoIndicatorVisibility
    
    fun onCreate(itemId : Int) {
        viewModelScope.launch {
            val items = getMediaItems()
            val item = items.firstOrNull() { it.id == itemId }
            item?.let {
                _title.value = item.title
                _thumb.value = item.url
                _videoIndicatorVisibility.value = item.type == Type.VIDEO
                }
            }
        }

    private suspend fun getMediaItems(): List<MediaItem> = withContext(ioDispatcher) {
        mediaProvider.getItems()
    }
}
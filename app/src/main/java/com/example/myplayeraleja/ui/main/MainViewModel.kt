package com.example.myplayeraleja.ui.main

import androidx.lifecycle.*
import com.example.myplayeraleja.data.Filter
import com.example.myplayeraleja.data.MediaItem
import com.example.myplayeraleja.data.MediaProvider
import com.example.myplayeraleja.ui.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _progressVisible = MutableLiveData<Boolean>()
    val progressVisible: LiveData<Boolean> get() = _progressVisible

    private val _items = MutableLiveData<List<MediaItem>>()
    val items: LiveData<List<MediaItem>> get() = _items

    private val _navigateToDetail = MutableLiveData<Event<Int>>()
    val navigateToDetail: LiveData<Event<Int>> get() = _navigateToDetail

    fun updateItems(filter: Filter = Filter.None) {
        viewModelScope.launch {
            _progressVisible.value = true
            _items.value = getFilteredItems(filter)
            _progressVisible.value = false
        }
    }

    private suspend fun getFilteredItems(filter: Filter): List<MediaItem> {
        return withContext(Dispatchers.IO) {
            MediaProvider.getItems().let { mediaItems ->
                when (filter) {
                    Filter.None -> mediaItems
                    is Filter.ByType -> mediaItems.filter { it.type == filter.type }
                }
            }
        }
    }

    fun onMediaItemClicked(mediaItem: MediaItem) {
        _navigateToDetail.value = Event(mediaItem.id)
    }
}
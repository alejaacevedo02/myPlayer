package com.example.myplayeraleja.ui.main

import androidx.lifecycle.*
import com.example.myplayeraleja.data.Filter
import com.example.myplayeraleja.data.MediaItem
import com.example.myplayeraleja.data.MediaProvider
import com.example.myplayeraleja.data.MediaProviderImpl
import com.example.myplayeraleja.ui.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val mediaProvider: MediaProvider = MediaProviderImpl,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

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
        return withContext(ioDispatcher) {
            mediaProvider.getItems().let { mediaItems ->
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
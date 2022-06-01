package com.example.myplayeraleja.ui.main

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.myplayeraleja.data.Filter
import com.example.myplayeraleja.data.MediaItem
import com.example.myplayeraleja.data.MediaProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenter(private val view: View, private val scope: CoroutineScope) {

    interface View {
        fun setProgressVisible(visible: Boolean)
        fun updateItems(items: List<MediaItem>)
        fun navigateToDetail(id: Int)
    }

    fun updateItems(filter: Filter = Filter.None) {
        scope.launch {
            view.setProgressVisible(true)
            val items = getFilteredItems(filter)
            view.updateItems(items)
            view.setProgressVisible(false)
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
        view.navigateToDetail(mediaItem.id)
    }
}
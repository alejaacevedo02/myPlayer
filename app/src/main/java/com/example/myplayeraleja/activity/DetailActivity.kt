package com.example.myplayeraleja.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.myplayeraleja.MediaItem
import com.example.myplayeraleja.MediaItem.*
import com.example.myplayeraleja.MediaProvider
import com.example.myplayeraleja.R
import com.example.myplayeraleja.databinding.ActivityDetailBinding
import com.example.myplayeraleja.loadUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "DetailActivity:id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val itemId = intent.getIntExtra(EXTRA_ID, -1)

        lifecycleScope.launch {
            val items = getMediaItems()
            val item = items.firstOrNull() { it.id == itemId }
            item?.let {
                supportActionBar?.title = item.title
                binding.detailThumb.loadUrl(item.url)
                binding.detailVideoIndicator.visibility = when (item.type) {
                    Type.PHOTO -> View.GONE
                    Type.VIDEO -> View.VISIBLE
                }
            }
        }
    }

    private suspend fun getMediaItems(): List<MediaItem> = withContext(Dispatchers.IO) {
        MediaProvider.getItems()
    }
}
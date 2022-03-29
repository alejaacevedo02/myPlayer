package com.example.myplayeraleja.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myplayeraleja.*
import com.example.myplayeraleja.MediaItem.Type
import com.example.myplayeraleja.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mediaGridAdapter =
        MediaGridAdapter { mediaItem -> startActivity<DetailActivity>(DetailActivity.EXTRA_ID to mediaItem.id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = mediaGridAdapter
        updateItems()
    }

    private fun updateItems(filter: Int = R.id.filter_all) {
        lifecycleScope.launch {
            binding.progress.visibility = View.VISIBLE
            mediaGridAdapter.items = withContext(Dispatchers.IO) { getFilteredItems(filter) }
            binding.progress.visibility = View.GONE
        }
    }

    private fun getFilteredItems(filter: Int): List<MediaItem> {
        return MediaProvider.getItems().let { mediaItems ->
            when (filter) {
                R.id.filter_all -> mediaItems
                R.id.filter_photos -> mediaItems.filter { it.type == Type.PHOTO }
                R.id.filter_videos -> mediaItems.filter { it.type == Type.VIDEO }
                else -> emptyList()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        updateItems(item.itemId)
        return super.onOptionsItemSelected(item)
    }
}
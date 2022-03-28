package com.example.myplayeraleja

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myplayeraleja.MediaItem.*
import com.example.myplayeraleja.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mediaGridAdapter =  MediaGridAdapter { toast(it.title) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = mediaGridAdapter
        updateItems()
    }

    private fun updateItems(filter: Int = R.id.filter_all){
        GlobalScope.launch(Dispatchers.Main) {
            binding.progress.visibility = View.VISIBLE
            mediaGridAdapter.items = withContext(Dispatchers.IO) { getFilterdItems(filter) }
            binding.progress.visibility = View.GONE
        }
    }

    private fun getFilterdItems(filter: Int): List<MediaItem>{
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
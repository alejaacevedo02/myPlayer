package com.example.myplayeraleja

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myplayeraleja.MediaItem.*
import com.example.myplayeraleja.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var job: Job

    private lateinit var binding: ActivityMainBinding

    private val mediaGridAdapter = MediaGridAdapter { toast(it.title) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = SupervisorJob()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = mediaGridAdapter
        updateItems()
    }

    private fun updateItems(filter: Int = R.id.filter_all) {
        launch {
            binding.progress.visibility = View.VISIBLE
            mediaGridAdapter.items = withContext(Dispatchers.IO) { getFilteredItems(filter) }
            binding.progress.visibility = View.GONE
        }
    }

    private fun getFilteredItems(filter: Int): List<MediaItem>{
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

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
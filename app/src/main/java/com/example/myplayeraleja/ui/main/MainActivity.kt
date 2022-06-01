package com.example.myplayeraleja.ui.main

import android.opengl.Visibility
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myplayeraleja.*
import com.example.myplayeraleja.data.MediaItem.Type
import com.example.myplayeraleja.data.Filter
import com.example.myplayeraleja.data.MediaItem
import com.example.myplayeraleja.data.MediaProvider
import com.example.myplayeraleja.databinding.ActivityMainBinding
import com.example.myplayeraleja.ui.detail.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private lateinit var binding: ActivityMainBinding
    private val mainPresenter = MainPresenter(this, lifecycleScope)
    private val mediaGridAdapter =
        MediaGridAdapter { mainPresenter.onMediaItemClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = mediaGridAdapter
        mainPresenter.updateItems()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter = when (item.itemId) {
            R.id.filter_photos -> Filter.ByType(Type.PHOTO)
            R.id.filter_videos -> Filter.ByType(Type.VIDEO)
            else -> Filter.None
        }
        mainPresenter.updateItems(filter)
        return super.onOptionsItemSelected(item)
    }

    override fun setProgressVisible(visible: Boolean) {
        binding.progress.visibility =   if (visible) View.VISIBLE else  View.GONE
    }

    override fun updateItems(items: List<MediaItem>) {
        mediaGridAdapter.items = items
    }

    override fun navigateToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.EXTRA_ID to id)
    }
}
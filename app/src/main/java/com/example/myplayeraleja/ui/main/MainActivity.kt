package com.example.myplayeraleja.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myplayeraleja.R
import com.example.myplayeraleja.data.Filter
import com.example.myplayeraleja.data.MediaItem
import com.example.myplayeraleja.data.MediaItem.Type
import com.example.myplayeraleja.databinding.ActivityMainBinding
import com.example.myplayeraleja.observe
import com.example.myplayeraleja.startActivity
import com.example.myplayeraleja.ui.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private val mediaGridAdapter = MediaGridAdapter { mainViewModel.onMediaItemClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(mainViewModel) {
            observe(progressVisible, ::setProgressVisible)

            observe(items, ::updateItems)

            observe(navigateToDetail) {
                it.getContentIfNotHandled()?.let { itemId ->
                    navigateToDetail(itemId)
                }
            }
        }
        binding.recycler.adapter = mediaGridAdapter
        mainViewModel.onFilterClicked()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter = when (item.itemId) {
            R.id.filter_photos -> Filter.ByType(Type.PHOTO)
            R.id.filter_videos -> Filter.ByType(Type.VIDEO)
            else -> Filter.None
        }
        mainViewModel.onFilterClicked(filter)
        return super.onOptionsItemSelected(item)
    }

    private fun setProgressVisible(visible: Boolean) {
        binding.progress.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun updateItems(items: List<MediaItem>) {
        mediaGridAdapter.items = items
    }

    private fun navigateToDetail(id: Int) {
        startActivity<DetailActivity>(DetailActivity.EXTRA_ID to id)
    }
}
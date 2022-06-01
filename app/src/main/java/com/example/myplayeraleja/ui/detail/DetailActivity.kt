package com.example.myplayeraleja.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.myplayeraleja.data.MediaItem
import com.example.myplayeraleja.data.MediaItem.*
import com.example.myplayeraleja.data.MediaProvider
import com.example.myplayeraleja.databinding.ActivityDetailBinding
import com.example.myplayeraleja.databinding.ActivityMainBinding
import com.example.myplayeraleja.loadUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "DetailActivity:id"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val itemId = intent.getIntExtra(EXTRA_ID, -1)
        detailViewModel = ViewModelProvider(this).get()
        initObservers()
        detailViewModel.onCreate(itemId)
    }


    private fun initObservers() {
        detailViewModel.title.observe(this) { title ->
            supportActionBar?.title = title
        }
        detailViewModel.thumb.observe(this) { url ->
            binding.detailThumb.loadUrl(url)
        }
        detailViewModel.videoIndicatorVisibility.observe(this) {
            binding.detailVideoIndicator.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}



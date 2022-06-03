package com.example.myplayeraleja.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myplayeraleja.databinding.ActivityDetailBinding
import com.example.myplayeraleja.getViewModel
import com.example.myplayeraleja.loadUrl
import com.example.myplayeraleja.observe

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
        detailViewModel = getViewModel {
            observe(title) { title -> supportActionBar?.title = title }
            observe(thumb) { url -> binding.detailThumb.loadUrl(url) }
            observe(videoIndicatorVisibility) {
                binding.detailVideoIndicator.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        val itemId = intent.getIntExtra(EXTRA_ID, -1)
        detailViewModel.onCreate(itemId)
    }
}



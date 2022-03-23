package com.example.myplayeraleja

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myplayeraleja.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mediaGridAdapter by lazy { MediaGridAdapter(getItems()) { toast(it.title) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = mediaGridAdapter
        mediaGridAdapter.items = getItems()
    }
}
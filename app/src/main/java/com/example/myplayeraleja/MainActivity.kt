package com.example.myplayeraleja

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.filter_all -> mediaGridAdapter.items = getItems()
            R.id.filter_photos -> mediaGridAdapter.items = getItems().filter{ it.type == MediaItem.Type.PHOTO}
            R.id.filter_videos -> mediaGridAdapter.items = getItems().filter{ it.type == MediaItem.Type.VIDEO}
        }
        return super.onOptionsItemSelected(item)
    }
}
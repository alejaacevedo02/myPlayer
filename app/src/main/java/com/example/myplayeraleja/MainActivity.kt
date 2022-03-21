package com.example.myplayeraleja

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myplayeraleja.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = MediaGridAdapter(
            getItems(),
        ) {
            Toast.makeText(this@MainActivity, it.title, Toast.LENGTH_SHORT).show()
        }


    }

    private fun toast(message: String) = Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
}
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
        val view = binding.root
        val text = "Hello Kotlin"
        binding.message.text = text
        setContentView(view)
        initListeners()
    }

    private fun initListeners() {
        binding.button.setOnClickListener {
            toast( " Hello : ${binding.inputMessage.text}")
        }
    }


    private fun toast(message: String) = Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()

}
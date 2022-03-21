package com.example.myplayeraleja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myplayeraleja.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toast("Hello World")
    }

    private fun toast(message: String) = Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()

}
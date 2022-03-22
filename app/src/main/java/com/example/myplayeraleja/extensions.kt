package com.example.myplayeraleja

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

fun RecyclerView.ViewHolder.toast(message: String) {
    itemView.context.toast(message)
}
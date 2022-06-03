package com.example.myplayeraleja

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

fun RecyclerView.ViewHolder.toast(message: String) {
    itemView.context.toast(message)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    /* The ViewGroup is the one that gives the context for the view*/
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.loadUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

//Allow to use generic parameters and not lose them at compile time
inline fun <reified T : Activity> Context.startActivity(vararg pairs: Pair<String, Any?>) {
    val bundle = bundleOf(*pairs)
    val intent = Intent(this, T::class.java).apply {
        putExtras(bundle)
    }
    startActivity(intent)
}

inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(): T =
    ViewModelProvider(this).get()

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer(observer))
}
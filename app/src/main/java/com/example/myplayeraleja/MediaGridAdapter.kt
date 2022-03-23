package com.example.myplayeraleja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplayeraleja.MediaItem.Type
import com.example.myplayeraleja.databinding.ViewMediaItemBinding
import kotlin.properties.Delegates

class MediaGridAdapter(
    mediaItems: List<MediaItem> = emptyList(),
    private val mediaClickedListener: (MediaItem) -> Unit
) : RecyclerView.Adapter<MediaGridAdapter.MediaViewHolder>() {

    //Modify items of the adapter using observable
     var items: List<MediaItem> by Delegates.observable(mediaItems) { _, _, _ -> notifyDataSetChanged() }

    inner class MediaViewHolder(private val binding: ViewMediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaItem: MediaItem) {
            with(mediaItem) {
                binding.mediaTitle.text = title
                binding.mediaThumb.loadUrl(url)
                binding.mediaVideoIndicator.visibility = when (type) {
                    Type.PHOTO -> View.GONE
                    Type.VIDEO -> View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding =
            ViewMediaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val mediaItem = items[position]
        holder.bind(mediaItem)
        holder.itemView.setOnClickListener {
            mediaClickedListener(mediaItem)
        }
    }
}
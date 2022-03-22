package com.example.myplayeraleja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myplayeraleja.MediaItem.Type
import com.example.myplayeraleja.databinding.ViewMediaItemBinding

class MediaGridAdapter(
    private val mediaItems: List<MediaItem>,
    private val mediaClickedListener: (MediaItem) -> Unit
) :
    RecyclerView.Adapter<MediaGridAdapter.MediaViewHolder>() {

    inner class MediaViewHolder(private val binding: ViewMediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaItem: MediaItem) {
            binding.mediaTitle.text = mediaItem.title
            binding.mediaThumb.loadUrl(mediaItem.url)
            binding.mediaVideoIndicator.visibility = when (mediaItem.type) {
                Type.PHOTO -> View.GONE
                Type.VIDEO ->  View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding =
            ViewMediaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaViewHolder(binding)
    }

    override fun getItemCount(): Int = mediaItems.size

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val mediaItem = mediaItems[position]
        holder.bind(mediaItem)
        holder.itemView.setOnClickListener {
            mediaClickedListener(mediaItem)
        }
    }
}
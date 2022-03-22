package com.example.myplayeraleja

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
            Glide.with(binding.root.context)
                .load(mediaItem.url)
                .into(binding.mediaThumb)
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
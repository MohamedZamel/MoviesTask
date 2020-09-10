package com.mohamedzamel.movies.features.movieDetails.flickrGallary

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mohamedzamel.movies.databinding.FlickerGalleryListItemBinding
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.data.FlickerSearchPhotosRespose.Photos.Photo

class FlickrGalleryAdapter :
    PagingDataAdapter<Photo, FlickrGalleryAdapter.FlickrGalleryViewHolder>(FlickrGalleryDiffCallback()) {

    class FlickrGalleryViewHolder(
        private val binding: FlickerGalleryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.photo?.let { photo ->
                    val uri = Uri.parse(photo.getPhotoUrl())
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    view.context.startActivity(intent)
                }
            }
        }

        fun bind(item: Photo) {
            binding.apply {
                photo = item
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: FlickrGalleryViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrGalleryViewHolder {
        return FlickrGalleryViewHolder(
            FlickerGalleryListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }
}

private class FlickrGalleryDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}

package com.pratthamarora.moviedb_clone.ui.cast.photo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pratthamarora.moviedb_clone.R
import com.pratthamarora.moviedb_clone.data.model.ProfileImage
import com.pratthamarora.moviedb_clone.di.glide.GlideApp
import com.pratthamarora.moviedb_clone.utils.Constants.POSTER_ORG_URL
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter : ListAdapter<ProfileImage, PhotoAdapter.PhotoViewHolder>(COMPARATOR) {

    class PhotoViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: ProfileImage) {

            itemView.apply {

                GlideApp.with(this)
                    .load("$POSTER_ORG_URL${photo.path}")
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .thumbnail(0.5f)
                    .into(ivPhoto)
            }

        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                return PhotoViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_photo, parent, false)
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        photo?.let {
            holder.bind(photo)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ProfileImage>() {

            override fun areItemsTheSame(oldItem: ProfileImage, newItem: ProfileImage): Boolean {
                return oldItem.path == newItem.path
            }

            override fun areContentsTheSame(oldItem: ProfileImage, newItem: ProfileImage): Boolean {
                return oldItem == newItem
            }
        }
    }

}
package com.pratthamarora.moviedb_clone.ui.moviedetail

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.pratthamarora.moviedb_clone.R
import com.pratthamarora.moviedb_clone.data.model.Cast
import com.pratthamarora.moviedb_clone.di.glide.GlideApp
import com.pratthamarora.moviedb_clone.utils.Constants.POSTER_ORG_URL
import com.pratthamarora.moviedb_clone.utils.toPx
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter : ListAdapter<Cast, CastAdapter.CastViewHolder>(COMPARATOR) {

    class CastViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cast: Cast) {

            itemView.apply {

                val padding = 8f.toPx()
                val width = Resources.getSystem().displayMetrics.widthPixels / 2.75
                val params = RecyclerView.LayoutParams(
                    width.toInt(),
                    RecyclerView.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginStart = padding.toInt()
                    marginEnd = padding.toInt()
                }

                layoutParams = params

                GlideApp.with(ivCast)
                    .load("$POSTER_ORG_URL${cast.profilePath}")
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .transform(CircleCrop())
                    .into(ivCast)

                tvName.text = cast.name

                setOnClickListener {
                    val action =
                        MovieDetailFragmentDirections.actionMovieDetailFragmentToCastFragment(cast)
                    it.findNavController().navigate(action)
                }
            }

        }

        companion object {
            fun from(parent: ViewGroup): CastViewHolder {
                return CastViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_cast, parent, false)
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = getItem(position)
        cast?.let {
            holder.bind(cast)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Cast>() {

            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        }
    }

}
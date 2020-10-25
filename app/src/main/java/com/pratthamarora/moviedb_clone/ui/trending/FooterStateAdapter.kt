package com.pratthamarora.moviedb_clone.ui.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pratthamarora.moviedb_clone.R
import kotlinx.android.synthetic.main.item_footer_state.view.*

class FooterStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<FooterStateAdapter.FooterStateViewHolder>() {

    class FooterStateViewHolder private constructor(
        itemView: View,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            itemView.apply {
                if (loadState is LoadState.Error) {
                    tvError.text = loadState.error.message
                }
                progressBar.isVisible = loadState is LoadState.Loading
                tvError.isVisible = loadState !is LoadState.Loading
                retryBtn.isVisible = loadState !is LoadState.Loading
            }
        }

        companion object {
            fun from(parent: ViewGroup, retry: () -> Unit): FooterStateViewHolder {
                return FooterStateViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_footer_state, parent, false),
                    retry
                )
            }
        }

    }

    override fun onBindViewHolder(holder: FooterStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterStateViewHolder {
        return FooterStateViewHolder.from(parent, retry)
    }
}
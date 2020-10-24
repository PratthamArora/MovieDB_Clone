package com.pratthamarora.moviedb_clone.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.pratthamarora.moviedb_clone.R
import com.pratthamarora.moviedb_clone.data.model.Movie
import com.pratthamarora.moviedb_clone.di.glide.GlideApp
import com.pratthamarora.moviedb_clone.utils.Constants.POSTER_BASE_URL
import com.pratthamarora.moviedb_clone.utils.Constants.POSTER_ORG_URL
import com.pratthamarora.moviedb_clone.utils.Status.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.movie_detail_fragment.*

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.movie_detail_fragment) {

    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.movie.observe(viewLifecycleOwner) {
            when (it.status) {
                SUCCESS -> {
                    showLoading(false)
                    it.data?.let { it1 -> updateUI(it1) }
                }
                ERROR -> {
                    showLoading(false)
                    Snackbar.make(
                        requireView(),
                        it.message.toString(),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                LOADING -> showLoading(true)

            }
        }
    }

    private fun updateUI(movie: Movie) {

        GlideApp.with(ivBackdrop).load("$POSTER_ORG_URL${movie.backdropPath}")
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error).into(ivBackdrop)

        GlideApp.with(ivPoster).load("$POSTER_BASE_URL${movie.posterPath}")
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(ivPoster)

        tvTitle.text = movie.title

        if (movie.genres != null && movie.genres.isNotEmpty()) {
            val genres = movie.genres.joinToString(separator = " | ", transform = { genre ->
                genre.name
            })
            tvGenres.text = genres
        } else {
            tvGenres.isVisible = false
        }
        if (movie.runtime != null) {
            tvRuntime.text = getString(R.string.format_runtime, movie.runtime)
        } else {
            tvRuntime.isVisible = false
        }
        if (movie.releaseDate != null && movie.releaseDate.isNotBlank()) {
            tvReleaseDate.text = movie.releaseDate
        } else {
            tvReleaseDate.isVisible = false
        }

        movie.voteAverage?.let {
            movieRating.rating = (it / 2).toFloat()
        }
        tvVoteCount.text = movie.voteCount.toString()
        tvOverviewBody.text = movie.overview
        parentLayout.isVisible = true


    }

    private fun showLoading(isShow: Boolean) {
        loadingContainer.isVisible = isShow
    }


}
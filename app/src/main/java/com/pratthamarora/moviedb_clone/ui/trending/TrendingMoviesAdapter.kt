package com.pratthamarora.moviedb_clone.ui.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pratthamarora.moviedb_clone.R
import com.pratthamarora.moviedb_clone.data.model.Movie
import com.pratthamarora.moviedb_clone.di.glide.GlideApp
import com.pratthamarora.moviedb_clone.utils.Constants.POSTER_BASE_URL
import kotlinx.android.synthetic.main.item_movie.view.*

class TrendingMoviesAdapter :
    RecyclerView.Adapter<TrendingMoviesAdapter.TrendingMoviesViewHolder>() {

    private var movies: List<Movie> = listOf()


    class TrendingMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {

            itemView.apply {
                GlideApp.with(moviePoster)
                    .load("$POSTER_BASE_URL${movie.posterPath}")
                    .into(moviePoster)
                movieTitleTV.text = movie.title
                movieReleaseDateTV.text = movie.releaseDate
                movieOverviewTV.text = movie.overview
                setOnClickListener {
                    val action =
                        TrendingFragmentDirections.actionTrendingFragmentToMovieDetailFragment(movie.id)
                    it.findNavController().navigate(action)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): TrendingMoviesViewHolder {
                return TrendingMoviesViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesViewHolder {
        return TrendingMoviesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TrendingMoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}
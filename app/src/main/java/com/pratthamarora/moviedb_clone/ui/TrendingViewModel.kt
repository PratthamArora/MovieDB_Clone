package com.pratthamarora.moviedb_clone.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratthamarora.moviedb_clone.data.model.Movie
import com.pratthamarora.moviedb_clone.data.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class TrendingViewModel @ViewModelInject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val _trendingMovies = MutableLiveData<List<Movie>>()
    val trendingMovies: LiveData<List<Movie>> get() = _trendingMovies

    init {
        movieRepository.getTrendingMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    _trendingMovies.value = response.results
                },
                { error -> Timber.e(error.localizedMessage) }
            )
    }
}
package com.pratthamarora.moviedb_clone.ui.trending

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.pratthamarora.moviedb_clone.data.model.Movie
import com.pratthamarora.moviedb_clone.data.repository.MovieRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TrendingViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val compoDisposable = CompositeDisposable()

    private val _trendingMovies = MutableLiveData<PagingData<Movie>>()
    val trendingMovies: LiveData<PagingData<Movie>> get() = _trendingMovies

    init {
        getTrendingMovies()
    }

    override fun onCleared() {
        compoDisposable.clear()
        super.onCleared()
    }

    private fun getTrendingMovies() {
        compoDisposable.add(
            movieRepository.getTrendingMovies()
                .cachedIn(viewModelScope)
                .subscribe {
                    _trendingMovies.value = it
                }
        )
    }

    fun onRefresh() {
        getTrendingMovies()
    }
}
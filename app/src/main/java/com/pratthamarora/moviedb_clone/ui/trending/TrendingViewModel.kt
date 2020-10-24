package com.pratthamarora.moviedb_clone.ui.trending

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratthamarora.moviedb_clone.data.model.Movie
import com.pratthamarora.moviedb_clone.data.repository.MovieRepository
import com.pratthamarora.moviedb_clone.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

class TrendingViewModel @ViewModelInject constructor(
    movieRepository: MovieRepository
) : ViewModel() {
    private val compoDisposable = CompositeDisposable()

    private val _trendingMovies = MutableLiveData<Resource<List<Movie>>>()
    val trendingMovies: LiveData<Resource<List<Movie>>> get() = _trendingMovies

    init {
        compoDisposable.add(
            movieRepository.getTrendingMovies()
                .doOnSubscribe { _trendingMovies.value = Resource.Loading(null) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        _trendingMovies.value = response.results?.let { Resource.Success(it) }
                    },
                    { error ->
                        run {
                            Timber.e(error)
                            _trendingMovies.value = Resource.Error(error.message!!, null)
                        }
                    }
                )
        )
    }

    override fun onCleared() {
        compoDisposable.clear()
        super.onCleared()
    }
}
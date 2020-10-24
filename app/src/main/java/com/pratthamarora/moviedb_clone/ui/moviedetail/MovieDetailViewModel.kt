package com.pratthamarora.moviedb_clone.ui.moviedetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.pratthamarora.moviedb_clone.data.model.Movie
import com.pratthamarora.moviedb_clone.data.repository.MovieRepository
import com.pratthamarora.moviedb_clone.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieDetailViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    private val _movie = MutableLiveData<Resource<Movie>>()
    val movie: LiveData<Resource<Movie>>
        get() = _movie
    private val compositeDisposable = CompositeDisposable()

    init {
        if (state.contains("movieId")) {
            val id = state.get<Long>("movieId")
            id?.let {
                compositeDisposable.add(
                    movieRepository.getMovieDetails(it)
                        .doOnSubscribe { _movie.value = Resource.Loading() }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { movie ->
                                _movie.value = Resource.Success(movie)
                            },
                            { throwable ->
                                _movie.value = throwable.message?.let { it1 -> Resource.Error(it1) }
                            })
                )
            }
        } else {
            _movie.value = Resource.Error("Cannot get arguments")
        }

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
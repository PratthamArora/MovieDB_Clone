package com.pratthamarora.moviedb_clone.ui.cast.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratthamarora.moviedb_clone.data.model.Cast
import com.pratthamarora.moviedb_clone.data.repository.MovieRepository
import com.pratthamarora.moviedb_clone.utils.Resource
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class AboutViewModel @AssistedInject constructor(
    movieRepository: MovieRepository,
    @Assisted private val castId: Long
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _cast = MutableLiveData<Resource<Cast>>()
    val cast: LiveData<Resource<Cast>>
        get() = _cast


    init {
        compositeDisposable.add(
            movieRepository.getCastDetails(castId)
                .doOnSubscribe { _cast.value = Resource.Loading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _cast.value = Resource.Success(it)
                    },
                    {
                        _cast.value = Resource.Error(it.localizedMessage)

                    }
                )
        )
    }


    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(castId: Long): AboutViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            castId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(castId) as T
            }

        }
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
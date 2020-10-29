package com.pratthamarora.moviedb_clone.ui.cast.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratthamarora.moviedb_clone.data.model.ProfileImage
import com.pratthamarora.moviedb_clone.data.repository.MovieRepository
import com.pratthamarora.moviedb_clone.utils.Resource
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PhotoViewModel @AssistedInject constructor(
    movieRepository: MovieRepository,
    @Assisted private val castId: Long
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _photo = MutableLiveData<Resource<List<ProfileImage>>>()
    val photo: LiveData<Resource<List<ProfileImage>>>
        get() = _photo


    init {
        compositeDisposable.add(
            movieRepository.getCastImages(castId)
                .doOnSubscribe { _photo.value = Resource.Loading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _photo.value = Resource.Success(it)
                    },
                    {
                        _photo.value = Resource.Error(it.localizedMessage)

                    }
                )
        )
    }


    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(castId: Long): PhotoViewModel
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
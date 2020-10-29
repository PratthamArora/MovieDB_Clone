package com.pratthamarora.moviedb_clone.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava3.flowable
import com.pratthamarora.moviedb_clone.data.model.Cast
import com.pratthamarora.moviedb_clone.data.model.Movie
import com.pratthamarora.moviedb_clone.data.model.ProfileImage
import com.pratthamarora.moviedb_clone.data.remote.MovieService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val pagingSource: MoviePagingSource
) {

    fun getTrendingMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { pagingSource }
        ).flowable

    fun getMovieDetails(movieId: Long): Single<Movie> =
        movieService.getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())

    fun getCastDetails(castId: Long): Single<Cast> =
        movieService.getCastDetails(castId)
            .subscribeOn(Schedulers.io())

    fun getCastImages(castId: Long): Single<List<ProfileImage>> =
        movieService.getCastImages(castId)
            .subscribeOn(Schedulers.io())
            .map {
                it.profiles
            }
}
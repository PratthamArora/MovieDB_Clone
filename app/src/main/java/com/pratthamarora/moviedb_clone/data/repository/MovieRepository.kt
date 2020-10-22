package com.pratthamarora.moviedb_clone.data.repository

import com.pratthamarora.moviedb_clone.data.model.Movies
import com.pratthamarora.moviedb_clone.data.remote.MovieService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService
) {

    fun getTrendingMovies(): Single<Movies> =
        movieService.getTrendingMovies()
            .subscribeOn(Schedulers.io())
}
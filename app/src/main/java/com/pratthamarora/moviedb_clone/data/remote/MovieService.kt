 package com.pratthamarora.moviedb_clone.data.remote

import com.pratthamarora.moviedb_clone.data.model.Movie
import com.pratthamarora.moviedb_clone.data.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

 interface MovieService {

    @GET("trending/all/day")
    fun getTrendingMovies(): Single<Movies>

    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId") movieId: Long): Single<Movie>
}
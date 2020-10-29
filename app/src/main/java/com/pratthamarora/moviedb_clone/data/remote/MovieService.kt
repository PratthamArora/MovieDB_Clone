package com.pratthamarora.moviedb_clone.data.remote

import com.pratthamarora.moviedb_clone.data.model.Cast
import com.pratthamarora.moviedb_clone.data.model.Movie
import com.pratthamarora.moviedb_clone.data.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/all/day")
    fun getTrendingMovies(@Query("page") page: Int): Single<Movies>

    @GET("movie/{movieId}?append_to_response=credits")
    fun getMovieDetails(@Path("movieId") movieId: Long): Single<Movie>

    @GET("person/{id}")
    fun getCastDetails(@Path("id") castId: Long): Single<Cast>
}
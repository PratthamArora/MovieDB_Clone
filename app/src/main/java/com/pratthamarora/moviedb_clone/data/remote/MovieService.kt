package com.pratthamarora.moviedb_clone.data.remote

import com.pratthamarora.moviedb_clone.BuildConfig
import com.pratthamarora.moviedb_clone.data.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface MovieService {
    companion object {
        const val CLIENT_KEY = BuildConfig.TMDB_API_KEY
    }

    @GET("trending/all/day?api_key=$CLIENT_KEY")
    fun getTrendingMovies(): Single<Movies>
}
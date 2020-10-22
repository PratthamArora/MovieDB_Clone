package com.pratthamarora.moviedb_clone.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Movies(
    val page: Long?,
    val results: List<Movie>?,
    @Json(name = "total_pages")
    val totalPages: Long?,
    @Json(name = "total_results")
    val totalResults: Long?
)
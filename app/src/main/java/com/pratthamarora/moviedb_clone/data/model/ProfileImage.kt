package com.pratthamarora.moviedb_clone.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileImage(
    val width: Int?,
    val height: Int?,
    @Json(name = "file_path")
    val path: String,
    @Json(name = "aspect_ratio")
    val aspectRatio: Double?
)
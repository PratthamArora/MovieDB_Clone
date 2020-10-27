package com.pratthamarora.moviedb_clone.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Credit(
    val cast:List<Cast>
)
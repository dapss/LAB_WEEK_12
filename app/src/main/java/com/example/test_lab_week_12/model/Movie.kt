package com.example.test_lab_week_12.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "release_date")
    val releaseDate: String?,

    val popularity: Double,

    @Json(name = "backdrop_path")
    val backdropPath: String? = null, // Fixed naming convention

    @Json(name = "vote_average")
    val voteAverage: Double? = null,

    @Json(name = "vote_count")
    val voteCount: Int? = null
)
package com.example.test_lab_week_12.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass // Import this

// Add this line!
@JsonClass(generateAdapter = true)
data class PopularMoviesResponse(
    @Json(name = "results")
    val results: List<Movie>
)
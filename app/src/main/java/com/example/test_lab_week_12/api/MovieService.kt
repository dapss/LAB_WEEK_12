package com.example.test_lab_week_12.api

import com.example.test_lab_week_12.model.PopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    // We use 'suspend' to allow this function to be paused and resumed
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse
}
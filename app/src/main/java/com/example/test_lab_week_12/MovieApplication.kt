package com.example.test_lab_week_12

import android.app.Application
import com.example.test_lab_week_12.api.MovieService
import com.example.test_lab_week_12.MovieRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication : Application() {

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        // Create a Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        // Create the MovieService
        val movieService = retrofit.create(MovieService::class.java)

        // Initialize the Repository
        movieRepository = MovieRepository(movieService)
    }
}
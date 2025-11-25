package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_lab_week_12.api.MovieService
import com.example.test_lab_week_12.model.Movie


class MovieRepository(private val movieService: MovieService) {

    // TODO: Paste your API Key here
    private val apiKey = "9221d7c6f4ea2de56a120fada76ba121"

    // LiveData to hold the list of movies
    private val movieLiveData = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = movieLiveData

    // LiveData to hold error messages
    private val errorLiveData = MutableLiveData<String>()
    val error: LiveData<String>
        get() = errorLiveData

    // Suspend function to fetch data asynchronously
    suspend fun fetchMovies() {
        try {
            // Get the list of popular movies from the API
            val popularMovies = movieService.getPopularMovies(apiKey)
            // Post the results to LiveData
            movieLiveData.postValue(popularMovies.results)
        } catch (exception: Exception) {
            // If an error occurs, post the error message
            errorLiveData.postValue("An error occurred: ${exception.message}")
        }
    }
}
package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_12.MovieRepository
import com.example.test_lab_week_12.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    init {
        fetchPopularMovies()
    }

    // Expose LiveData to the UI
    val popularMovies: LiveData<List<Movie>>
        get() = movieRepository.movies

    val error: LiveData<String>
        get() = movieRepository.error

    // Fetch movies using a Coroutine
    private fun fetchPopularMovies() {
        // Launch in viewModelScope on the IO thread
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
        }
    }
}
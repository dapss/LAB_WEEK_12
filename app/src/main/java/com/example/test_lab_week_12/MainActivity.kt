package com.example.test_lab_week_12

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_12.model.Movie
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)

        val movieAdapter = MovieAdapter { movie ->
            // Optional: Handle click
        }
        recyclerView.adapter = movieAdapter

        val movieRepository = (application as MovieApplication).movieRepository

        val movieViewModel = ViewModelProvider(
            this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return MovieViewModel(movieRepository) as T
                }
            })[MovieViewModel::class.java]

        // Fetch movies from the API using Flow collection
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Launch a coroutine to collect movies
                launch {
                    movieViewModel.popularMovies.collect { movies ->
                        // --- ASSIGNMENT LOGIC ---
                        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

                        val filteredMovies = movies
                            .filter { movie ->
                                // Check if release date exists and starts with current year
                                movie.releaseDate?.startsWith(currentYear) == true
                            }
                            .sortedByDescending {
                                // Sort by popularity descending
                                it.popularity
                            }

                        // Add the FILTERED list to the adapter
                        movieAdapter.addMovies(filteredMovies)
                    }
                }

                // Launch a separate coroutine to collect errors
                launch {
                    movieViewModel.error.collect { error ->
                        if (error.isNotEmpty()) {
                            Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}
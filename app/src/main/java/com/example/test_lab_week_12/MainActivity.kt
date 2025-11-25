package com.example.test_lab_week_12

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_12.model.Movie
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)

        // Initialize adapter
        val movieAdapter = MovieAdapter { movie ->
            // Optional: Add click handling
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

        // Observe the movie list LiveData
        // FIX: Explicitly specify the type of the 'movies' parameter
        movieViewModel.popularMovies.observe(this) { movies: List<Movie>? ->
            // Check if 'movies' is not null before using .filter
            if (movies != null) {
                val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()

                val filteredMovies = movies
                    .filter { movie ->
                        // Ensure releaseDate is not null and matches the year
                        movie.releaseDate?.startsWith(currentYear) == true
                    }
                    .sortedByDescending {
                        it.popularity
                    }

                movieAdapter.addMovies(filteredMovies)
            }
        }

        // Observe errors
        movieViewModel.error.observe(this) { error ->
            if (error.isNotEmpty()) {
                Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}

package com.mohamedzamel.movies.features.MoviesList.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamedzamel.movies.shared.database.MoviesRepository
import com.mohamedzamel.movies.shared.database.entities.Movie

class MoviesListViewModel(var moviesRepository: MoviesRepository) : ViewModel() {

    val movies: LiveData<List<Movie>> = moviesRepository.getMovies()
}
package com.mohamedzamel.movies.features.moviesList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamedzamel.movies.features.moviesList.data.MoviesDataSource
import com.mohamedzamel.movies.features.moviesList.data.repo.local.LocalMoviesRepository

/**
 * Factory for creating a [MoviesListViewModel] with a constructor that takes a [LocalMoviesRepository]
 *
 */
class MoviesListViewModelFactory(private var moviesRepository: MoviesDataSource) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesListViewModel(moviesRepository) as T
    }
}

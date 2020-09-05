package com.mohamedzamel.movies.features.MoviesList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamedzamel.movies.shared.database.MoviesRepository

/**
 * Factory for creating a [MoviesListViewModel] with a constructor that takes a [MoviesRepository]
 *
 */
class MoviesListViewModelFactory(var moviesRepository: MoviesRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesListViewModel(moviesRepository) as T
    }
}
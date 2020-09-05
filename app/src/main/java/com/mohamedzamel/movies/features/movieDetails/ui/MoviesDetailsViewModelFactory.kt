package com.mohamedzamel.movies.features.movieDetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.data.FlickerRepository

/**
 * Factory for creating a [MovieDetailsViewModel] with a constructor that takes a [flickerRepository]
 *
 */
class MoviesDetailsViewModelFactory(var flickerRepository: FlickerRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(flickerRepository) as T
    }
}

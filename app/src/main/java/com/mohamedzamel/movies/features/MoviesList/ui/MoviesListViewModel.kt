package com.mohamedzamel.movies.features.MoviesList.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedzamel.movies.features.MoviesList.data.MoviesDataSource
import com.mohamedzamel.movies.shared.database.entities.Movie
import kotlinx.coroutines.launch
import java.util.TreeMap

class MoviesListViewModel(private var moviesDataSource: MoviesDataSource) : ViewModel() {

    val movies: LiveData<List<Movie>>
        get() {
            _isLoading.postValue(true)

            return moviesDataSource.getMovies()
        }

    private var searchResultTreeMap = TreeMap<Int, List<Movie>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchedMovies = MutableLiveData<TreeMap<Int, List<Movie>>>()
    val searchedMovies: LiveData<TreeMap<Int, List<Movie>>> = _searchedMovies

    fun getTopFiveMoviesByYearAndTitle(query: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            searchResultTreeMap = moviesDataSource.getTopFiveMoviesByYearAndTitle(query)
            _searchedMovies.postValue(searchResultTreeMap)
            _isLoading.postValue(false)
        }
    }
}

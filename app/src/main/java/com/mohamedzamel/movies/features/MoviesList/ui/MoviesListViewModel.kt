package com.mohamedzamel.movies.features.MoviesList.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamedzamel.movies.features.MoviesList.data.MoviesRepository
import com.mohamedzamel.movies.shared.database.entities.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.TreeMap

class MoviesListViewModel(private var moviesRepository: MoviesRepository) : ViewModel() {

    val movies: LiveData<List<Movie>>
        get() {
            _isLoading.postValue(true)

            return moviesRepository.getMovies()
        }

    private var searchResultTreeMap = TreeMap<Int, List<Movie>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchedMovies = MutableLiveData<TreeMap<Int, List<Movie>>>()
    val searchedMovies: LiveData<TreeMap<Int, List<Movie>>> = _searchedMovies

    /**
     * Make years query to get them then pass every on of it to another query to get result
     * I made it into view model because i was thinking to list of years as a tags
     *
     * more explanation what happen here is to get list of years and search with them in the query in concurrence way way
     */
    fun getTopFiveMoviesByYearAndTitle(query: String) {
        _isLoading.postValue(true)

        CoroutineScope(Dispatchers.IO).launch {
            val listOfYears = moviesRepository.getYears()
            listOfYears.map { year ->
                async {
                    val movieList = moviesRepository.getTopFiveMoviesByYearAndTitle(year, query)
                    searchResultTreeMap.set(
                        key = year,
                        movieList
                    )
                }
            }.awaitAll()
            _searchedMovies.postValue(searchResultTreeMap)
            _isLoading.postValue(false)
        }
    }

    /**
     * another approch to get the top 5 movies
     */
    fun getTopFiveMoviesQueryGroupedByYear(query: String): LiveData<List<Movie>> {

        return moviesRepository.getTopFiveMoviesByYear(query)
    }
}

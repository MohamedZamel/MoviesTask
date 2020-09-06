package com.mohamedzamel.movies.features.MoviesList.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mohamedzamel.movies.features.MoviesList.data.MoviesRepository
import com.mohamedzamel.movies.shared.database.entities.Movie
import java.util.*

class MoviesListViewModel(private var moviesRepository: MoviesRepository) : ViewModel() {

    val movies: LiveData<List<Movie>> = moviesRepository.getMovies()
    private var tempHashMap = TreeMap<Int, List<Movie>>()
    val years: LiveData<List<Int>> = moviesRepository.getYears()

    /**
     * Make years query to get them then pass every on of it to another query to get result
     * I made it into view model because i was thinking to list of years as a tags
     */
    fun getTopFiveMoviesByYearAndTitle(lifecycleOwner: LifecycleOwner, query: String) {
        years.observe(lifecycleOwner, { it ->
            val totalYears = it.size
            it.forEachIndexed { index, currentYear ->
                val result = moviesRepository.getTopFiveMoviesByYearAndTitle(currentYear, query)
                result.observe(lifecycleOwner, {
                    tempHashMap.set(currentYear, it)
                    if (index == (totalYears.minus(1))) {
                        _searchedMovies.value = tempHashMap
                        tempHashMap = TreeMap<Int, List<Movie>>()
                    }
                })
            }
        })
    }

    /**
     * another approch to get the top 5 movies
     */
    fun getTopFiveMoviesQueryGroupedByYear(query: String): LiveData<List<Movie>> {

        return moviesRepository.getTopFiveMoviesByYear(query)
    }

    private
    val _searchedMovies = MutableLiveData<TreeMap<Int, List<Movie>>>()

    val searchedMovies: LiveData<TreeMap<Int, List<Movie>>> = _searchedMovies


}
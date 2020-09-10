package com.mohamedzamel.movies.features.moviesList.data

import androidx.lifecycle.LiveData
import com.mohamedzamel.movies.features.moviesList.data.entities.Movie
import java.util.TreeMap

interface MoviesDataSource {

    fun getMovies(): LiveData<List<Movie>>
    suspend fun getTopFiveMoviesByYearAndTitle(query: String): TreeMap<Int, List<Movie>>
}

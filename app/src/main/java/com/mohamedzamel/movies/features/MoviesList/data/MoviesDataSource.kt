package com.mohamedzamel.movies.features.MoviesList.data

import androidx.lifecycle.LiveData
import com.mohamedzamel.movies.shared.database.entities.Movie
import java.util.TreeMap

interface MoviesDataSource {

    fun getMovies(): LiveData<List<Movie>>
    suspend fun getTopFiveMoviesByYearAndTitle(query: String): TreeMap<Int, List<Movie>>
}

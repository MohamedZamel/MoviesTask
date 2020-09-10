package com.mohamedzamel.movies.features.MoviesList.data

import androidx.lifecycle.LiveData
import com.mohamedzamel.movies.shared.database.asFileAndMemorySearch.Movie

interface MoviesDataSource {

    suspend fun getMovies(): LiveData<List<Movie>>
    suspend fun getTopFiveMoviesByYearAndTitle(query: String)
}

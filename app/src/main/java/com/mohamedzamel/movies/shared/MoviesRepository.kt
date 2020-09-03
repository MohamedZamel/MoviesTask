package com.mohamedzamel.movies.shared

import com.mohamedzamel.movies.shared.database.MovieDao


/**
 * Repo for handling movie data operations  as singleton object
 */
class MoviesRepository private constructor(private val movieDao: MovieDao) {
    //retrieve all movies from db
    fun getMovies() = movieDao.getMovies()

    //retrieve all movies from db TODO handle the requirement [Each search result category will hold at most the ​top rated 5 movies​ of this category (year​)​.]
    fun getTopFiveMoviesByYear(movieTitle: String) = movieDao.getTopFiveMoviesByYear(movieTitle)

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(movieDao: MovieDao) =
            instance ?: synchronized(this) {
                instance ?: MoviesRepository(movieDao).also { instance = it }
            }
    }

}


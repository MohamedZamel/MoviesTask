package com.mohamedzamel.movies.features.MoviesList.data

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.mohamedzamel.movies.shared.database.MovieDao
import com.mohamedzamel.movies.shared.database.entities.Movie

/**
 * [MoviesRepository] for handling movie data operations  as singleton object
 */
class MoviesRepository private constructor(private val movieDao: MovieDao) {
    // retrieve all movies from db
    fun getMovies() = movieDao.getMovies()
    suspend fun getYears() = movieDao.getYears()
    fun getTopFiveMoviesByYearAndTitle(year: Int, query: String): List<Movie> {
        return movieDao.getTopFiveMoviesByYearAndTitle(year, "%$query%")
    }

    // retrieve all movies from db
    fun getTopFiveMoviesByYear(movieTitle: String): LiveData<List<Movie>> {
        val query =
            SimpleSQLiteQuery(
                "select movies.title,movies.rating,movies.year , movies.cast,movies.genres,count(*) from movies,movies as dup where  dup.title like '%$movieTitle%' and movies.title like '%$movieTitle%'  " +
                    "and movies.year=dup.year and  (movies.rating < dup.rating or " +
                    "( movies.rating = dup.rating and movies.title > dup.title)) group by movies.title,movies.rating,movies.year,movies.cast,movies.genres having count(*) <= 5 order by movies.year DESC"
            )
        return movieDao.getTopFiveMoviesByYear(query)
    }

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

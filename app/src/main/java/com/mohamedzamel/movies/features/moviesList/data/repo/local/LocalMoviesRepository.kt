package com.mohamedzamel.movies.features.moviesList.data.repo.local

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.mohamedzamel.movies.features.moviesList.data.MoviesDataSource
import com.mohamedzamel.movies.features.moviesList.data.database.MovieDao
import com.mohamedzamel.movies.features.moviesList.data.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.util.TreeMap

/**
 * [LocalMoviesRepository] for handling movie data operations  as singleton object
 */
class LocalMoviesRepository private constructor(private val movieDao: MovieDao) : MoviesDataSource {
    val TAG = LocalMoviesRepository::javaClass.name

    // retrieve all movies from db
    override fun getMovies() = movieDao.getMovies()

    fun getTopFiveMoviesByYearAndTitle(year: Int, query: String): List<Movie> {
        return movieDao.getTopFiveMoviesByYearAndTitle(year, "%$query%")
    }

    /**
     * Make years query to get them then pass every on of it to another query to get result
     * I made it into view model because i was thinking to list of years as a tags
     * more explanation what happen here is to get list of years and search with them in the query in concurrence way way
     */
    override suspend fun getTopFiveMoviesByYearAndTitle(query: String): TreeMap<Int, List<Movie>> =
        coroutineScope {

            withContext(Dispatchers.IO) {

                val searchResultTreeMap = TreeMap<Int, List<Movie>>()
                val listOfYears = movieDao.getYears()
                listOfYears.map { year ->
                    async {
                        val movieList = getTopFiveMoviesByYearAndTitle(year, query)

                        searchResultTreeMap.set(
                            key = year,
                            movieList
                        )
                    }
                }.awaitAll()
                searchResultTreeMap
            }
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
        private var instance: LocalMoviesRepository? = null

        fun getInstance(movieDao: MovieDao) =
            instance ?: synchronized(this) {
                instance ?: LocalMoviesRepository(movieDao).also { instance = it }
            }
    }
}

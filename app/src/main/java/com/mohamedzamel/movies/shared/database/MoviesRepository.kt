package com.mohamedzamel.movies.shared.database

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.mohamedzamel.movies.shared.database.entities.Movie


/**
 * Repo for handling movie data operations  as singleton object
 */
class MoviesRepository private constructor(private val movieDao: MovieDao) {
    //retrieve all movies from db
    fun getMovies() = movieDao.getMovies()
    fun getTopFiveMoviesByYearAndTitle(year: Int, query: String): LiveData<List<Movie>> {
        return movieDao.getTopFiveMoviesByYearAndTitle(year, "%$query%")

    }

    fun getYears(): LiveData<List<Int>> {
        return movieDao.getYears()
    }

    //retrieve all movies from db
    fun getTopFiveMoviesByYear(movieTitle: String): LiveData<List<Movie>> {
        var query: SimpleSQLiteQuery =
            SimpleSQLiteQuery("select movies.title,movies.rating,movies.year , movies.cast,movies.genres,count(*) from movies,movies as dup where  dup.title like '%${movieTitle}%' and movies.title like '%${movieTitle}%'  and movies.year=dup.year and  (movies.rating < dup.rating or ( movies.rating = dup.rating and movies.title > dup.title)) group by movies.title,movies.rating,movies.year,movies.cast,movies.genres having count(*) <= 5 order by movies.year DESC")
        return movieDao.getTopFiveMoviesByYear(query)

    }


    //    //retrieve all years from db
//    fun getAllYears():LiveData<List<Int>> {
//        var query: SimpleSQLiteQuery =
//            SimpleSQLiteQuery("select * from ( select *,row_number() over(partition  by year order by rating desc ) as rn from movies ) where rn<=5 and  ( title like '%action%' or genres like '%action%' )")
//        return    movieDao.getTopFiveMoviesByYear(query)
//
//    }
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


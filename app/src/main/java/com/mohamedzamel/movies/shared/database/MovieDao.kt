package com.mohamedzamel.movies.shared.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohamedzamel.movies.shared.database.entities.Movie


/**
 * Data access object for Movie app
 */
@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY title")
    fun getMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM movies WHERE title LIKE :movieTitle ORDER BY year")
    fun getTopFiveMoviesByYear(movieTitle: String): LiveData<List<Movie>>


}
package com.mohamedzamel.movies.shared.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
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

    @Query("SELECT Distinct year from movies")
    fun getYears(): LiveData<List<Int>>

    @Query("Select * From movies Where year = :year and title like :query order by rating desc limit 5")
    fun getTopFiveMoviesByYearAndTitle(year: Int, query: String): LiveData<List<Movie>>


    //    /* select * from (select *,row_number() over(partition  by year order by rating desc ) as rn from movies ) where rn<=5 and  ( title like '%action%' or genres like '%action%' )
//  select distinct year from movies
//  select * from movies where year = 2011 and title like '%a%' order by rating desc limit 5
//*/
    @RawQuery(
        observedEntities = [Movie::class]
    )


    fun getTopFiveMoviesByYear(query: SimpleSQLiteQuery): LiveData<List<Movie>>

}










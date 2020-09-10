package com.mohamedzamel.movies.features.moviesList.data.repo.inMemory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mohamedzamel.movies.features.moviesList.data.MoviesDataSource
import com.mohamedzamel.movies.features.moviesList.data.entities.AllMovies
import com.mohamedzamel.movies.features.moviesList.data.entities.Movie
import com.mohamedzamel.movies.features.moviesList.data.repo.inMemory.searchUtils.Searcher
import java.util.TreeMap

interface YearHolder {
    val year: Int
    val rating: Int
}

class InMemoryRepo(var moviesAsString: String) : MoviesDataSource {

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: InMemoryRepo? = null

        fun getInstance(moviesAsString: String) =
            instance ?: synchronized(this) {
                instance ?: InMemoryRepo(moviesAsString).also { instance = it }
            }
    }

    override fun getMovies(): LiveData<List<Movie>> {

        val moviesList = Gson().fromJson(moviesAsString, AllMovies::class.java)
        val moviesLiveData = MutableLiveData<List<Movie>>()
        moviesLiveData.postValue(moviesList.movies)
        return moviesLiveData
    }

    override suspend fun getTopFiveMoviesByYearAndTitle(query: String): TreeMap<Int, List<Movie>> {

        return searchByTitle(moviesAsString, query)
    }

    fun searchByTitle(fileAsString: String, queryText: String): TreeMap<Int, List<Movie>> {
        val file = fileAsString
        val gson = Gson()
        val srch = Searcher.fromJSON(file) {
            it.asJsonObject?.get("movies")?.asJsonArray?.map {
                gson.fromJson(it, Movie::class.java)
            }?.toList() ?: listOf()
        }

        return srch.getTopN(5) { queryText in it.title }
    }

    fun test() {

        val jsonStr = moviesAsString
        val gson = Gson()
        val searcher = Searcher.fromJSON(jsonStr) {
            it.asJsonObject?.get("movies")?.asJsonArray?.map {
                gson.fromJson(it, Movie::class.java)
            }?.toList() ?: listOf<Movie>()
        }
        searcher.getTopN(3) { "j" in it.title }
    }
}

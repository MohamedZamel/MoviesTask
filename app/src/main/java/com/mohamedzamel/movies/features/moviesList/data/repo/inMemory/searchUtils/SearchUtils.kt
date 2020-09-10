package com.mohamedzamel.movies.features.moviesList.data.repo.inMemory.searchUtils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.mohamedzamel.movies.features.moviesList.data.entities.Movie
import com.mohamedzamel.movies.features.moviesList.data.repo.inMemory.YearHolder
import com.mohamedzamel.movies.shared.Constants
import java.io.File
import java.util.TreeMap

class Searcher<T : YearHolder>(target: List<T>) {
    private val groupedTarget by lazy {
        target.sortedBy { it.rating }.groupBy { it.year }
    }

    fun getTopN(n: Int, predicate: (T) -> Boolean): TreeMap<Int, List<T>> {
        val dd = TreeMap<Int, List<T>>()

        groupedTarget.map {

                (year, value) ->

            val r = value.asSequence().filter(
                predicate
            ).take(n).toList()

            dd.set(year, r)

            dd
        }
        return dd
    }

    companion object {
        fun <T : YearHolder> fromJSON(
            json: String,
            transformer: (e: JsonElement) -> List<T>
        ): Searcher<T> {
            val element = JsonParser.parseString(json)
            return Searcher(transformer(element))
        }
    }
}

fun loadableIntoMemory(applicationContext: Context): String {

    applicationContext.assets.open(Constants.MOVIES_DATA_FILENAME).use { inputStream ->
        return inputStream.bufferedReader().readLines().joinToString(separator = "")
    }
}

fun test() {

    val jsonStr = File("//android_asset/movies.json").readLines().joinToString(separator = "")
    val gson = Gson()
    val searcher = Searcher.fromJSON(jsonStr) {
        it.asJsonObject?.get("movies")?.asJsonArray?.map {
            gson.fromJson(it, Movie::class.java)
        }?.toList() ?: listOf<Movie>()
    }
    searcher.getTopN(3) { "j" in it.title }
}

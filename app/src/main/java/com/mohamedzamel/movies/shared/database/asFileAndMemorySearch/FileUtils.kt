package com.mohamedzamel.movies.shared.database.asFileAndMemorySearch

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser.parseString
import java.io.File

interface YearHolder {
    val year: String
    val rating: Int
}

data class Movie(
    val title: String,
    override val year: String,
    val cast: Array<String>,
    val genres: Array<String>,
    override val rating: Int
) : YearHolder

class Searcher<T : YearHolder>(target: List<T>) {
    private val groupedTarget by lazy {
        target.sortedBy { it.rating }.groupBy { it.year }
    }

    fun getTopN(n: Int, predicate: (T) -> Boolean): List<T> = groupedTarget.flatMap { (_, value) ->
        value.asSequence().filter(
            predicate
        ).take(n).toList()
    }

    companion object {
        fun <T : YearHolder> fromJSON(
            json: String,
            transformer: (e: JsonElement) -> List<T>
        ): Searcher<T> {
            val element = parseString(json)
            return Searcher(transformer(element))
        }
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
    Log.d(
        "search in memory",
        "test: ${searcher.getTopN(3) { "j" in it.title }.joinToString(separator = "\n")}"
    )
}

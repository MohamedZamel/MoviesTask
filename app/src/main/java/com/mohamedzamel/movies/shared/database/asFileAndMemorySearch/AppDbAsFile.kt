package com.mohamedzamel.movies.shared.database.asFileAndMemorySearch

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.mohamedzamel.movies.shared.Constants

fun loadableIntoMemory(applicationContext: Context): String {

    applicationContext.assets.open(Constants.MOVIES_DATA_FILENAME).use { inputStream ->
        return inputStream.bufferedReader().readLines().joinToString(separator = "")
    }
}

fun SearchByTitle(fileAsString: String, queryText: String) {
    val file = fileAsString
    val gson = Gson()
    val srch = Searcher.fromJSON(file) {
        it.asJsonObject?.get("movies")?.asJsonArray?.map {
            gson.fromJson(it, Movie::class.java)
        }?.toList() ?: listOf<Movie>()
    }

    srch.getTopN(5) { queryText in it.title }

    Log.d("test", "${srch.getTopN(5) { queryText in it.title }.size}")
}

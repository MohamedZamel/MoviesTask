package com.mohamedzamel.movies.shared.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.mohamedzamel.movies.shared.Constants.MOVIES_DATA_FILENAME
import com.mohamedzamel.movies.shared.database.AppDb
import com.mohamedzamel.movies.shared.database.entities.AllMovies
import kotlinx.coroutines.coroutineScope

/**
 * Important worker to load local json file into room db
 **/

class MoviesDbWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(MOVIES_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->

//                    val moviesType = object : TypeToken<List<Movie>>() {}.type
                    val moviesJsonObj: AllMovies =
                        Gson().fromJson(jsonReader, AllMovies::class.java)
                    Log.d(TAG, "doWork: $moviesJsonObj")
                    val database = AppDb.getInstance(applicationContext)
                    database.movieDao().insertAll(moviesJsonObj.movies)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error movies database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "MoviesDatabaseWorker"
    }
}
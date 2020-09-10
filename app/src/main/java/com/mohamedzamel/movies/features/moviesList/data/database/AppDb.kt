package com.mohamedzamel.movies.features.moviesList.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mohamedzamel.movies.features.moviesList.data.entities.Movie
import com.mohamedzamel.movies.shared.Constants.DATABASE_NAME
import com.mohamedzamel.movies.shared.workers.MoviesDbWorker

/**
 * Room Db for the whole app
 **/
@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDb? = null
        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDb {
            return Room.databaseBuilder(context, AppDb::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<MoviesDbWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}

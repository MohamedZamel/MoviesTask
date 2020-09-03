package com.mohamedzamel.movies.shared

import android.content.Context
import com.mohamedzamel.movies.shared.database.AppDb

object InjectorUtils {

    fun provideMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepository.getInstance(
            AppDb.getInstance(context.applicationContext).movieDao()
        )
    }


}
package com.mohamedzamel.movies.shared

import android.content.Context
import androidx.fragment.app.Fragment
import com.mohamedzamel.movies.features.MoviesList.ui.MoviesListViewModelFactory
import com.mohamedzamel.movies.shared.database.AppDb
import com.mohamedzamel.movies.shared.database.MoviesRepository

object InjectorUtils {

    /**
     * create and prepare [MoviesRepository] method
     */
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepository.getInstance(
            AppDb.getInstance(context.applicationContext).movieDao()
        )
    }

    /**
     *  create and provide [MoviesListViewModelFactory] to needed requester
     */
    fun provideMoviesListViewModelFactory(fragment: Fragment): MoviesListViewModelFactory {
        return MoviesListViewModelFactory(getMoviesRepository(fragment.requireContext()))
    }


}
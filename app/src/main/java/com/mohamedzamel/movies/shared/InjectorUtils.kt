package com.mohamedzamel.movies.shared

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.mohamedzamel.movies.BuildConfig
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.api.FlickrService
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.data.FlickerRepository
import com.mohamedzamel.movies.features.movieDetails.ui.MoviesDetailsViewModelFactory
import com.mohamedzamel.movies.features.moviesList.data.MoviesDataSource
import com.mohamedzamel.movies.features.moviesList.data.database.AppDb
import com.mohamedzamel.movies.features.moviesList.data.repo.inMemory.InMemoryRepo
import com.mohamedzamel.movies.features.moviesList.data.repo.inMemory.searchUtils.loadableIntoMemory
import com.mohamedzamel.movies.features.moviesList.data.repo.local.LocalMoviesRepository
import com.mohamedzamel.movies.features.moviesList.ui.MoviesListViewModelFactory

object InjectorUtils {

    /**
     * create and Choose [LocalMoviesRepository]  or [InMemoryRepo] for  [MoviesDataSource] method
     */
    private fun moviesDataSource(context: Context): MoviesDataSource {
        Log.d("dddd", "moviesDataSource: ${BuildConfig.IS_IN_MEMORY_REPO} ")
        return if (BuildConfig.IS_IN_MEMORY_REPO.isEmpty() || BuildConfig.IS_IN_MEMORY_REPO.equals("false")) {
            LocalMoviesRepository.getInstance(
                AppDb.getInstance(context.applicationContext).movieDao()
            )
        } else {
            InMemoryRepo.getInstance(
                loadableIntoMemory(context)
            )
        }
    }

    /**
     *  create and provide [MoviesListViewModelFactory] to needed requester
     */
    fun provideMoviesListViewModelFactory(fragment: Fragment): MoviesListViewModelFactory {
        return MoviesListViewModelFactory(moviesDataSource(fragment.requireContext()))
    }

    /**
     * create and prepare [getFlickerRepository] method
     */
    private fun getFlickerRepository(): FlickerRepository {
        return FlickerRepository(FlickrService.create())
    }

    /**
     *  create and provide [MoviesDetailsViewModelFactory] to needed requester
     */
    fun provideMoviesDetailsViewModelFactory(): MoviesDetailsViewModelFactory {
        return MoviesDetailsViewModelFactory(getFlickerRepository())
    }
}

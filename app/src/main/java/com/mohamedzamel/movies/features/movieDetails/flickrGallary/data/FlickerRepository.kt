package com.mohamedzamel.movies.features.movieDetails.flickrGallary.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.api.FlickrService
import kotlinx.coroutines.flow.Flow

/**
 * Flicker Repo which responsible for loading photos from api and connect it to pager
 */
class FlickerRepository(var service: FlickrService) {

    /*
    get search result function as flow with paging
     */
    fun getSearchResultStream(queryText: String): Flow<PagingData<FlickerSearchPhotosRespose.Photos.Photo>> {

        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { FlickerPagingSource(service, queryText) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 100
    }
}

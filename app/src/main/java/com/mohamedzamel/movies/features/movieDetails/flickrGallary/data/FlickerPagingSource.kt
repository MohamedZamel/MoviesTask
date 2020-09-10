package com.mohamedzamel.movies.features.movieDetails.flickrGallary.data

import androidx.paging.PagingSource
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.api.FlickrService

/**
 * constant to start paging from index 1
 */
private const val FLICKR_SERVICE_STARTING_PAGE_INDEX = 1

/**
 * pagingSource to load 100 result per page and enable endless scrolling to the photos
 */
class FlickerPagingSource(var service: FlickrService, private val queryText: String) :
    PagingSource<Int, FlickerSearchPhotosRespose.Photos.Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FlickerSearchPhotosRespose.Photos.Photo> {
        val page = params.key ?: FLICKR_SERVICE_STARTING_PAGE_INDEX
        return try {
            // call service with new paging params values
            val response = service.searchPhotos(queryText, page, params.loadSize)
            val photos = response.photos.photo
            LoadResult.Page(
                data = photos,
                prevKey = if (page == FLICKR_SERVICE_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.photos.total.toInt()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}

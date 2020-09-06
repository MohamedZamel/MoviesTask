package com.mohamedzamel.movies.features.movieDetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mohamedzamel.movies.BuildConfig
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.data.FlickerRepository
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.data.FlickerSearchPhotosRespose
import kotlinx.coroutines.flow.Flow

class MovieDetailsViewModel internal constructor(private var flickerRepository: FlickerRepository) :
    ViewModel() {

    private var currentQueryText: String? = null
    private var currentSearchResult: Flow<PagingData<FlickerSearchPhotosRespose.Photos.Photo>>? =
        null

    //get photos from api
    fun searchForPhotos(queryText: String): Flow<PagingData<FlickerSearchPhotosRespose.Photos.Photo>> {

        currentQueryText = queryText

        val newPhotos: Flow<PagingData<FlickerSearchPhotosRespose.Photos.Photo>> =
            flickerRepository.getSearchResultStream(queryText).cachedIn(viewModelScope)
        currentSearchResult = newPhotos

        return newPhotos
    }

    fun hasValidFlickerKey() = (BuildConfig.FLICKR_ACCESS_KEY != "null")

}


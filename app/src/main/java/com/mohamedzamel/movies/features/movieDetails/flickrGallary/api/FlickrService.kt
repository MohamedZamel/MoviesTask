package com.mohamedzamel.movies.features.movieDetails.flickrGallary.api


import com.mohamedzamel.movies.BuildConfig
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.data.FlickerSearchPhotosRespose
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used to connect to the flickr API to fetch photos
 */
interface FlickrService {
    // example to the request https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=1a16930a3fe92d19c7a9b4b3b3931b82&text=apple&format=json&nojsoncallback=1
    @GET("services/rest/")
    suspend fun searchPhotos(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("method") method: String = "flickr.photos.search",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("format") format: String = "json",
        @Query("api_key") apiKey: String = BuildConfig.FLICKR_ACCESS_KEY
    ): FlickerSearchPhotosRespose


    companion object {
        private const val BASE_URL = "https://www.flickr.com/"

        fun create(): FlickrService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FlickrService::class.java)
        }
    }
}

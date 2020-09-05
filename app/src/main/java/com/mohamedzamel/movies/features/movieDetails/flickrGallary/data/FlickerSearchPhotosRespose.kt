package com.mohamedzamel.movies.features.movieDetails.flickrGallary.data


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FlickerSearchPhotosRespose(
    @SerializedName("photos")
    var photos: Photos,
    @SerializedName("stat")
    var stat: String
) {
    @Keep
    data class Photos(
        @SerializedName("page")
        var page: Int,
        @SerializedName("pages")
        var pages: Int,
        @SerializedName("perpage")
        var perpage: Int,
        @SerializedName("photo")
        var photo: List<Photo>,
        @SerializedName("total")
        var total: String
    ) {
        @Keep
        data class Photo(
            @SerializedName("farm")
            var farm: Int,
            @SerializedName("id")
            var id: String,
            @SerializedName("secret")
            var secret: String,
            @SerializedName("server")
            var server: String,
            @SerializedName("title")
            var title: String,

            ) {
            fun getPhotoUrl(): String {
                return "https://farm$farm.static.flickr.com/$server/$id".plus("_")
                    .plus("$secret.jpg")
            }
        }
    }
}
package com.mohamedzamel.movies.shared.database.entities

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @SerializedName("cast")
    var cast: List<String>,
    @SerializedName("genres")
    var genres: List<String>,
    @SerializedName("rating")
    var rating: Int,
    @SerializedName("title")
    @PrimaryKey @ColumnInfo(name = "title")
    var title: String,
    @SerializedName("year")
    var year: Int
) : Parcelable

data class AllMovies(@SerializedName("movies") var movies: List<Movie>)

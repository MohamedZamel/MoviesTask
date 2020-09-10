package com.mohamedzamel.movies.features.moviesList.data.entities

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mohamedzamel.movies.features.moviesList.data.repo.inMemory.YearHolder
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
    override val rating: Int,
    @SerializedName("title")
    @PrimaryKey @ColumnInfo(name = "title")
    var title: String,
    @SerializedName("year")
    override val year: Int

) : Parcelable, YearHolder

data class AllMovies(@SerializedName("movies") var movies: List<Movie>)

package com.mohamedzamel.movies.shared.database

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * Converters to allow room to read complex types ex Strings , custom data types ... etc
 */
class Converters {

    //region string list to string obj
    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)
    //endregion

    //region from string obj to string list
    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
//endregion

}
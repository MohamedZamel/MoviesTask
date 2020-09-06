package com.mohamedzamel.movies.shared

/**
 * extention function [List] to remove brackets  from [String]
 */
fun List<String>.toHandyString(): String {

    return toString().removePrefix("[").removeSuffix("]")
}



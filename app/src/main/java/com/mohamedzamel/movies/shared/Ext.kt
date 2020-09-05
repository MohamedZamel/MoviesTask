package com.mohamedzamel.movies.shared

/**
 * extention function [List] to remove brackets  from [String]
 */
fun List<String>.toHandyString(): String {

  val text = this.toString().removePrefix("[").removeSuffix("]")

  return text
}



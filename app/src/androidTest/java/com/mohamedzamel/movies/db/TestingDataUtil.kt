package com.mohamedzamel.movies.db

import com.mohamedzamel.movies.features.moviesList.data.entities.Movie

/**
 * [Movie] objects used for tests.
 */
val testMovies = arrayListOf(
    Movie(
        arrayListOf("zamel 1", "omar 2"),
        arrayListOf("comedy", "action"),
        4,
        "Zamel in wonderland",
        2020
    ),
    Movie(
        arrayListOf("omar 1", "magdy 2"),
        arrayListOf("roman"),
        5,
        "omar in magic land",
        1000
    ),
    Movie(
        arrayListOf("magdy  1", "omar 2", "zamel 1", "omar 2"),
        arrayListOf("comedy", "action", "romance"),
        2,
        "omar w zamel in wonderland",
        2009
    ),
    Movie(
        arrayListOf("zxfc 1", "omxdr 2"),
        arrayListOf("comedy", "action"),
        4,
        "z in fanta land",
        2002
    )
)

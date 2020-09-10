package com.mohamedzamel.movies.features.moviesList.data.database

import com.mohamedzamel.movies.features.moviesList.data.entities.Movie
import com.mohamedzamel.movies.shared.toHandyString
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertersTest {
    private lateinit var movie: Movie

    @Before
    fun setUp() {
        movie = Movie(arrayListOf("dd", "Dd", "dd"), arrayListOf(), 5, "test", 2009)
    }

    @Test
    fun removedBrackets() {
        assertEquals("dd, Dd, dd", movie.cast.toHandyString())
    }
}

package com.mohamedzamel.movies.shared.database

import com.mohamedzamel.movies.shared.database.entities.Movie
import com.mohamedzamel.movies.shared.toHandyString
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExtTest {
    private lateinit var movie: Movie

    @Before
    fun setUp() {

        movie = Movie(arrayListOf("dd", "Dd", "dd"), arrayListOf(), 5, "test", 2009)

    }

    @Test
    fun removedbrackets() {
        assertEquals("dd, Dd, dd", movie.cast.toHandyString())

    }


}
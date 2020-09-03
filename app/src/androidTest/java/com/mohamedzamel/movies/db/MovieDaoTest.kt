package com.mohamedzamel.movies.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mohamedzamel.movies.shared.database.AppDb
import com.mohamedzamel.movies.shared.database.MovieDao
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*

class MovieDaoTest {
    private lateinit var db: AppDb
    private lateinit var movieDaoTest: MovieDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDb::class.java).build()
        movieDaoTest = db.movieDao()

        db.movieDao().insertAll(testMovies)

    }


    @After
    fun closeDb() {
        db.close()
    }


    @Test
    fun testGetMovies() = runBlocking {
        Assert.assertThat(getValue(movieDaoTest.getMovies()).size, Matchers.equalTo(4))


    }
}
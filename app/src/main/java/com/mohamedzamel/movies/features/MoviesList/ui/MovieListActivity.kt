package com.mohamedzamel.movies.features.MoviesList.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mohamedzamel.movies.R

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_movie_list)
        setSupportActionBar(findViewById(R.id.toolbar))


    }
}
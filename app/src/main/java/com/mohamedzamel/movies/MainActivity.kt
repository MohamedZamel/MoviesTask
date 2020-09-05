package com.mohamedzamel.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
//		InjectorUtils.provideMoviesRepository(this).getTopFiveMoviesByYear("%a%")
//			.observe(this, Observer {
//				Log.d("zame", "onCreate: ${it.count()}  ${it[1].genres[0]}")
//				Toast.makeText(this, it[1].title.toString(), Toast.LENGTH_SHORT).show()
//			})
	}
}
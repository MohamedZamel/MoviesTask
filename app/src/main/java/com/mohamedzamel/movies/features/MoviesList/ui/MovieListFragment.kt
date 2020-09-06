package com.mohamedzamel.movies.features.MoviesList.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mohamedzamel.movies.R
import com.mohamedzamel.movies.databinding.FragmentShowMoviesListBinding
import com.mohamedzamel.movies.features.MoviesList.MoviesAdapter
import com.mohamedzamel.movies.shared.InjectorUtils
import java.util.*

/**
 * A [MovieListFragment] to Show list of the movie
 */
class MovieListFragment : Fragment() {

    private val viewModel: MoviesListViewModel by viewModels {
        InjectorUtils.provideMoviesListViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentShowMoviesListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = MoviesAdapter()
        binding.moviesList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.movies_title_toolbar)
            it.setDisplayHomeAsUpEnabled(false)

        }
    }

    private fun subscribeUi(adapter: MoviesAdapter) {
        viewModel.movies.observe(viewLifecycleOwner) { plants ->
            adapter.submitList(plants)
        }
        Log.d("timeZamel", "subscribeUi t1: ${Calendar.getInstance().timeInMillis}")
        viewModel.moviesRepository.getTopFiveMoviesByYear("a").observe(viewLifecycleOwner) {
            Log.d("timeZamel", "subscribeUi: ${it.size}")
            Log.d("timeZamel", "subscribeUi t2 : ${Calendar.getInstance().timeInMillis}")

        }
        Log.d("timeZamel", "subscribeUi: years ${Calendar.getInstance().timeInMillis}}")

        viewModel.moviesRepository.getYears().observe(viewLifecycleOwner) {
            Log.d("timeZamel", "subscribeUi: ${it.size}")

            it.forEachIndexed { index, year ->
                viewModel.moviesRepository.getTopFiveMoviesByYearAndTitle(year, "a")
                    .observe(viewLifecycleOwner,
                        androidx.lifecycle.Observer {
                            Log.d("timeZamel", "subscribeUi: t$index  y$year ${it.size}")
                        })
            }
        }
    }

}
package com.mohamedzamel.movies.features.MoviesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mohamedzamel.movies.databinding.FragmentShowMoviesListBinding
import com.mohamedzamel.movies.features.MoviesList.MoviesAdapter
import com.mohamedzamel.movies.shared.InjectorUtils

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
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

    private fun subscribeUi(adapter: MoviesAdapter) {
        viewModel.movies.observe(viewLifecycleOwner) { plants ->
            adapter.submitList(plants)
        }
    }

}
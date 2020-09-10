package com.mohamedzamel.movies.features.moviesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.mohamedzamel.movies.R
import com.mohamedzamel.movies.databinding.FragmentShowMoviesListBinding
import com.mohamedzamel.movies.features.moviesList.MoviesAdapter
import com.mohamedzamel.movies.features.moviesList.YearsSection
import com.mohamedzamel.movies.shared.InjectorUtils
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

/**
 * A [MovieListFragment] to Show list of the movie
 */
class MovieListFragment : Fragment() {

    private val viewModel: MoviesListViewModel by viewModels {
        InjectorUtils.provideMoviesListViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentShowMoviesListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.mainLayout.showLoading()
        // SearchByTitle(loadfileIntoMemory(requireActivity()), "a")
        addLoadingObserver(binding)
        showBaseMovieList(binding)
        attachSearchViewListener(binding)
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun addLoadingObserver(binding: FragmentShowMoviesListBinding) {
        viewModel.isLoading.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    binding.mainLayout.showLoading()
                } else {
                    binding.mainLayout.showContent()
                }
            }
        )
    }

    private fun attachSearchViewListener(binding: FragmentShowMoviesListBinding) {
        binding.movieSearchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        searchMovieWithQuery(binding.moviesList, it)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        if (newText.isNotEmpty()) {
                            searchMovieWithQuery(binding.moviesList, newText)
                        } else {
                            showBaseMovieList(binding)
                        }
                    }
                    return false
                }
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
    }

    /**
     * setup toolbar
     */
    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = getString(R.string.movies_title_toolbar)
            it.setDisplayHomeAsUpEnabled(false)
        }
    }

    /**
     * show base movie list when when needed
     */
    private fun showBaseMovieList(binding: FragmentShowMoviesListBinding) {

        val adapter = MoviesAdapter()
        binding.moviesList.adapter = adapter
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)

            binding.mainLayout.showContent()
        }
    }
    //region search handle

    /**
     * search takes [RecyclerView] to show the result and the string query
     */
    private fun searchMovieWithQuery(recyclerView: RecyclerView, text: String) {

        viewModel.searchedMovies.observe(
            viewLifecycleOwner,
            {
                val sectionAdapter = SectionedRecyclerViewAdapter()
                it.forEach { row ->
                    sectionAdapter.addSection(
                        YearsSection(
                            row.value,
                            row.key.toString(),
                            recyclerView
                        )
                    )
                }
                recyclerView.adapter = sectionAdapter
            }
        )
        viewModel.getTopFiveMoviesByYearAndTitle(text)
    }
    //endregion
}

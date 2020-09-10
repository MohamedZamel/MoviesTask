package com.mohamedzamel.movies.features.movieDetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.mohamedzamel.movies.R
import com.mohamedzamel.movies.databinding.FragmentShowMovieDetailsBinding
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.FlickrGalleryAdapter
import com.mohamedzamel.movies.shared.InjectorUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A [ShowMovieDetailsFragment] to Show details of the movie
 */
class ShowMovieDetailsFragment : Fragment() {
    private val adapter = FlickrGalleryAdapter()
    private val args: ShowMovieDetailsFragmentArgs by navArgs()
    private var searchPicturesJob: Job? = null
    private val viewModel: MovieDetailsViewModel by viewModels {
        InjectorUtils.provideMoviesDetailsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentShowMovieDetailsBinding.inflate(inflater, container, false)
        if (viewModel.hasValidFlickerKey()) {
            lookup(args.movie.title)
        }
        setupUi(binding)
        return binding.root
    }

    private fun setupUi(binding: FragmentShowMovieDetailsBinding) {
        binding.flickrList.adapter = adapter
        binding.movie = args.movie
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle the up button here
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) ||
            super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.title = args.movie.title
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            it.setHomeButtonEnabled(true)
        }
    }

    private fun lookup(queryText: String) {
        // Make sure we cancel the previous search before creating a new one
        searchPicturesJob?.cancel()
        searchPicturesJob = lifecycleScope.launch {
            viewModel.searchForPhotos(queryText).collectLatest {

                adapter.submitData(it)
            }
        }
    }
}

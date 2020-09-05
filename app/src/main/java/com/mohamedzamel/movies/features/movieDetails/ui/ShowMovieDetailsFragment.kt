package com.mohamedzamel.movies.features.movieDetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mohamedzamel.movies.databinding.FragmentShowMovieDetailsBinding
import com.mohamedzamel.movies.features.movieDetails.flickrGallary.FlickrGalleryAdapter
import com.mohamedzamel.movies.shared.InjectorUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A MovieDetailsFragment to Show details of the movie
 */
class ShowMovieDetailsFragment : Fragment() {
    private val adapter = FlickrGalleryAdapter()
    private val args: ShowMovieDetailsFragmentArgs by navArgs()
    private var searchPicturesJob: Job? = null
    private val viewModel: MovieDetailsViewModel by viewModels {
        InjectorUtils.provideMoviesDetailsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentShowMovieDetailsBinding.inflate(inflater, container, false)
        lookup(args.movie.title)

        binding.flickrList.adapter = adapter
//        binding.toolbar.setNavigationOnClickListener { view ->
//            view.findNavController().navigateUp()
//        }


        return binding.root
    }

//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            findNavController().navigate(R.id.action_movie_list_fragment_to_movie_details_fragment)
//        }

    private fun lookup(queryText: String) {
        // Make sure we cancel the previous search before creating a new one
        searchPicturesJob?.cancel()
        searchPicturesJob = lifecycleScope.launch {
            viewModel.searchForPhotos(queryText).collectLatest {
                adapter.submitData(it)
                it.toString()
//                Log.d("zamel", "lookup: ${it.toString()}")
            }
        }
    }
}
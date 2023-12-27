package com.sefa.movies.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.sefa.movies.R
import com.sefa.movies.databinding.FragmentDetailsBinding
import com.sefa.movies.databinding.FragmentMainBinding
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.utils.Constants
import com.sefa.movies.utils.Constants.BASE_IMAGE_URL
import java.text.DecimalFormat

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        movie = args.movie
        setupMovie()
        return binding.root
    }

    private fun setupMovie()
    {
        val decimalFormat = DecimalFormat("#.#")

        binding.apply {
            toolbar.title = movie.title
            toolbar.setOnClickListener {
                goBackToHome()
            }
            textViewStarNumber.text = decimalFormat.format(movie.vote_average)
            imageViewMoviePoster.load(BASE_IMAGE_URL + movie.poster_path)
            textViewReleaseDate.text = movie.release_date
            textViewOverview.text =movie.overview
        }
    }

    private fun goBackToHome()
    {
        findNavController().popBackStack()
    }
}
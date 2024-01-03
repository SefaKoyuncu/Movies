package com.sefa.movies.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.sefa.movies.R
import com.sefa.movies.databinding.FragmentDetailsBinding
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.presentation.viewmodel.DetailsViewModel
import com.sefa.movies.utils.Constants.BASE_IMAGE_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var movie: Movie
    private val viewModel : DetailsViewModel by viewModels()
    private val isMovieExist = MutableStateFlow<Boolean>(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        movie = args.movie

        checkMovie(movie)
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

            setFavIcon()

            imageViewFavIcon.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                        manageFav(movie)
                        checkMovie(movie)
                        Log.e("TAG", "fav icon tıklandı")
                }
            }
        }
    }

    private fun goBackToHome()
    {
        findNavController().popBackStack()
    }

    private fun setFavIcon()
    {
        viewLifecycleOwner.lifecycleScope.launch{
                isMovieExist.collectLatest{ check ->
                    binding.imageViewFavIcon.setImageResource(if (check) R.drawable.fav else R.drawable.un_fav)
                }
        }
    }

    private fun manageFav(movie: Movie)
    {
        viewLifecycleOwner.lifecycleScope.launch {
                Log.e("TAG","manageFav-isMovieExist üstü")
                isMovieExist.collectLatest{ exist ->

                    if (exist)
                        deleteFromRoom(movie.id)
                    else
                        addToRoom(movie)

                    val iconResource = if (exist) R.drawable.un_fav else R.drawable.fav
                    binding.imageViewFavIcon.setImageResource(iconResource)
                }
        }
    }

    private fun addToRoom(movie: Movie)
    {
        viewModel.insertMovieDb(movie=movie)
    }

    private fun deleteFromRoom(movieID: Int)
    {
        viewModel.deleteFromDb(movieID=movieID)
    }

    private fun checkMovie(movie: Movie)
    {
        viewLifecycleOwner.lifecycleScope.launch {
                viewModel.checkIfMovieExists(movieID = movie.id)
                viewModel.getIsMovieExistInDb_.collect{
                    isMovieExist.value = it
                    Log.e("TAG", "stateflow" + it.toString())
                }
        }
    }
}
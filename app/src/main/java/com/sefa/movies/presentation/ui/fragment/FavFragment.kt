package com.sefa.movies.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sefa.movies.R
import com.sefa.movies.databinding.FragmentFavBinding
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.presentation.ui.adapter.LoaderStateAdapter
import com.sefa.movies.presentation.ui.adapter.PagingMovieAdapter
import com.sefa.movies.presentation.viewmodel.FavViewModel
import com.sefa.movies.utils.Gone
import com.sefa.movies.utils.Visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private val viewModel : FavViewModel by viewModels()
    private lateinit var moviePagingMovieAdapter: PagingMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fav,container,false)
        setupRV()
        return binding.root
    }

    private fun setupRV()
    {
        moviePagingMovieAdapter = PagingMovieAdapter{
            onItemSelected(it)
        }

        binding.rv.apply {
            val moviePagingAdapter = moviePagingMovieAdapter.withLoadStateFooter(
                footer = LoaderStateAdapter { moviePagingMovieAdapter.retry() }
            )
            adapter = moviePagingAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        observeMovies()
    }

    private fun observeMovies()
    {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                moviePagingMovieAdapter.addLoadStateListener { loadState ->
                    when (val refreshState = loadState.source.refresh)
                    {
                        is LoadState.Loading -> binding.progressBar.Visible()

                        is LoadState.Error -> {
                            binding.progressBar.Gone()
                            handleError(refreshState.error.message.toString()) }

                        is LoadState.NotLoading -> {
                            binding.progressBar.Gone()

                            if ( loadState.append.endOfPaginationReached )
                            {
                                if (moviePagingMovieAdapter.itemCount < 1)
                                {
                                    binding.textViewNoData.Visible()
                                    binding.rv.Gone()
                                }
                                else
                                {
                                    binding.textViewNoData.Gone()
                                    binding.rv.Visible()
                                }
                            }
                        }
                    }
                }

                viewModel.getMoviesPagingData.collect{ pagingData->
                    moviePagingMovieAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
                }
            }
        }
    }

    fun onItemSelected(movie: Movie)
    {
        Log.e("TAG",movie.title)
        val action : NavDirections = FavFragmentDirections.fromFavToDetails(movie)
        findNavController().navigate(action)
    }

    private fun handleError(error: String?)
    {
        Toast.makeText(context, error ?: "Ooops, error loading data!", Toast.LENGTH_LONG).show()
    }
}
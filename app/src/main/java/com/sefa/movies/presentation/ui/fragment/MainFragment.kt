package com.sefa.movies.presentation.ui.fragment

import android.os.Bundle
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
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sefa.movies.R
import com.sefa.movies.databinding.FragmentMainBinding
import com.sefa.movies.presentation.ui.adapter.LoaderStateAdapter
import com.sefa.movies.presentation.ui.adapter.PagingMovieAdapter
import com.sefa.movies.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var moviePagingMovieAdapter: PagingMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        setupRV()
        return binding.root
    }
    private fun setupRV()
    {
        moviePagingMovieAdapter = PagingMovieAdapter()

        binding.rv.apply {
            val moviePagingAdapter = moviePagingMovieAdapter.withLoadStateFooter(
                footer = LoaderStateAdapter { moviePagingMovieAdapter.retry() }
            )
            adapter = moviePagingAdapter
            layoutManager =LinearLayoutManager(context)
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
                        is LoadState.Loading -> showLoadingState()
                        is LoadState.Error -> showErrorState(refreshState.error)
                        is LoadState.NotLoading -> hideLoadingState()
                    }
                }

                viewModel.getMoviesPagingData.collect{ pagingData->
                    moviePagingMovieAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
                }
            }
        }
    }

    private fun showLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingState() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorState(error: Throwable) {
        handleError(error.message.toString())
        binding.progressBar.visibility = View.GONE
    }

    private fun handleError(error: String?)
    {
        Toast.makeText(context, error ?: "Ooops, error loading data!", Toast.LENGTH_LONG).show()
    }
}
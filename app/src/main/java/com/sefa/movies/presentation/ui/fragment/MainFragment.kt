package com.sefa.movies.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sefa.movies.R
import com.sefa.movies.data.util.Resource
import com.sefa.movies.databinding.FragmentMainBinding
import com.sefa.movies.presentation.ui.adapter.LoaderStateAdapter
import com.sefa.movies.presentation.ui.adapter.PagingMovieAdapter
import com.sefa.movies.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val getDataViewModel : MainViewModel by viewModels()
    private lateinit var moviePagingMovieAdapter: PagingMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        getPagingData()
    }

    private fun getPagingData()
    {
        getDataViewModel.getMoviesPagingData.observe(viewLifecycleOwner){ moviesList->
            when(moviesList)
            {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    moviesList.data?.let { moviePagingMovieAdapter.submitData(viewLifecycleOwner.lifecycle, it)}
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    val message = moviesList.message ?: "Oops!, data didn't pull..."
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}
package com.sefa.movies.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sefa.movies.R
import com.sefa.movies.data.util.Resource
import com.sefa.movies.databinding.FragmentMainBinding
import com.sefa.movies.presentation.ui.adapter.MovieAdapter
import com.sefa.movies.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val getDataViewModel : MainViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

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
        movieAdapter = MovieAdapter()
        binding.rv.apply {
            adapter=movieAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        getData()
    }

    private fun getData()
    {
        getDataViewModel.getMovies.observe(viewLifecycleOwner){ moviesList->
            when(moviesList)
            {
                is Resource.Success -> {
                    moviesList.data?.let {
                        for (movie in it)
                        {
                            Log.e("TAG-mFragment","Movie info :  Title : ${movie.title} Vote Average : ${movie.vote_average}")
                        }
                    }

                    binding.progressBar.visibility = View.GONE
                    moviesList.data?.let { movieAdapter.submitList(it) }
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
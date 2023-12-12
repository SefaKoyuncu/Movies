package com.sefa.movies.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.sefa.movies.R
import com.sefa.movies.data.util.Resource
import com.sefa.movies.databinding.FragmentMainBinding
import com.sefa.movies.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val getDataViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        getData()
        return binding.root
    }

    private fun getData()
    {
        getDataViewModel.getMovies.observe(viewLifecycleOwner){ moviesList->
            when(moviesList)
            {
                is Resource.Success -> {

                    moviesList.data?.get(0).let { Log.e("TAG","MainFragment ${it.toString()}")}
                    moviesList.data?.let {
                        for (movie in it)
                        {
                            Log.e("TAG","Movie info :  Title : ${movie.title} Vote Average : ${movie.vote_average} Overview : ${movie.overview}")
                        }
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(context,"Oops!, data didn't pull...",Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading ->{
                    Toast.makeText(context,"Loading...",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
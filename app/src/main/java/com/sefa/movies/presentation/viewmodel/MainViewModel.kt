package com.sefa.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.usecase.GetMoviesFromAPIUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val getMoviesFromAPIUseCase: GetMoviesFromAPIUseCase) : ViewModel()
{
    private val moviesPagingData_ = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val getMoviesPagingData: StateFlow<PagingData<Movie>>
        get() = moviesPagingData_

    init {
        observeMovies()
    }

    private fun observeMovies()
    {
        viewModelScope.launch {
            getMoviesFromAPIUseCase.invoke()
                .cachedIn(viewModelScope)
                .collect{ pagingData->
                    moviesPagingData_.value = pagingData
                }
        }
    }
}
package com.sefa.movies.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sefa.movies.data.util.Resource
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel()
{
    private val moviesPagingData_ = MutableLiveData<Resource<PagingData<Movie>>>()
    val getMoviesPagingData : LiveData<Resource<PagingData<Movie>>>
        get() = moviesPagingData_

    init {
        getPagingData()
    }

    private fun getPagingData() = viewModelScope.launch {
        moviesPagingData_.value = (Resource.Loading())
        try {
            getMoviesUseCase.getPagingData()
                .catch{
                    moviesPagingData_.value = (Resource.Error(it.message.toString()))
                }
                .collect{ resource->
                    resource.data?.let {list->
                        moviesPagingData_.value = (Resource.Success(list))
                    }
                }
        }
        catch (e : Exception)
        {
            moviesPagingData_.postValue(Resource.Error(e.localizedMessage?.toString() ?: "Oops!, data didn't pull"))
        }
    }
}
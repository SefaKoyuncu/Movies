package com.sefa.movies.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val movies_ = MutableLiveData<Resource<List<Movie>>>()
    val getMovies : LiveData<Resource<List<Movie>>>
        get() = movies_

    init {
        getData()
    }

    private fun getData() = viewModelScope.launch {
        movies_.postValue(Resource.Loading())
        try {
            getMoviesUseCase.invoke()
                .catch{
                    movies_.postValue(Resource.Error(it.message.toString()))
                }
                .collect{ resource->
                    resource.data?.let {list->
                        movies_.postValue(Resource.Success(list))
                    }
                }
        }
        catch (e : Exception)
        {
            movies_.postValue(Resource.Error(e.localizedMessage?.toString() ?: "Oops!, data didn't pull"))
        }
    }
}
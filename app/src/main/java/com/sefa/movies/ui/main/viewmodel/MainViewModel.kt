package com.sefa.movies.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sefa.movies.data.model.Result
import com.sefa.movies.data.repository.GetDataRepository
import com.sefa.movies.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val repo : GetDataRepository) : ViewModel()
{
    private val response_ = MutableLiveData<Resource<List<Result>?>>()
    val getMovies : LiveData<Resource<List<Result>?>>
        get() = response_

    init {
        getData()
    }

    private fun getData() = viewModelScope.launch {
        response_.postValue(Resource.Loading())
        try {
            repo.fetchData()
                .catch {
                    response_.postValue(Resource.Error(it.message.toString()))
                }
                .collect{
                    it?.let { res->
                        response_.postValue(Resource.Success(res.data?.results))
                    }
                }
        }
        catch (e : Exception)
        {
            response_.postValue(Resource.Error("Oops!, data didn't pull"))
        }
    }
}
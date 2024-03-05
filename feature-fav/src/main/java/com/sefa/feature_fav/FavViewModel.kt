package com.sefa.feature_fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sefa.domain.model.SingleMovie
import com.sefa.domain.usecase.GetFavMoviesFromDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel
@Inject
constructor(private val getFavMoviesFromDbUseCase: GetFavMoviesFromDbUseCase) : ViewModel()
{
    private val moviesPagingData_ = MutableStateFlow<PagingData<SingleMovie>>(PagingData.empty())
    val getMoviesPagingData: StateFlow<PagingData<SingleMovie>>
        get() = moviesPagingData_

    init {
        observeMovies()
    }

    fun observeMovies()
    {
        viewModelScope.launch {
            getFavMoviesFromDbUseCase.invoke()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect{ pagingData->
                    moviesPagingData_.value = pagingData
                }
        }
    }
}
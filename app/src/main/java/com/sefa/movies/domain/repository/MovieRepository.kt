package com.sefa.movies.domain.repository

import androidx.paging.PagingData
import com.sefa.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository
{
    fun getPagingData() : Flow<PagingData<Movie>>
}
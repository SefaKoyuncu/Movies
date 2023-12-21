package com.sefa.movies.domain.repository

import androidx.paging.PagingData
import com.sefa.movies.data.util.Resource
import com.sefa.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository
{
    suspend fun getPagingData() : Flow<Resource<PagingData<Movie>>>
}
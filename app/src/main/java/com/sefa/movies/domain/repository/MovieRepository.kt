package com.sefa.movies.domain.repository

import com.sefa.movies.data.util.Resource
import com.sefa.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository
{
    suspend fun fetchData() : Flow<Resource<List<Movie>>>
}
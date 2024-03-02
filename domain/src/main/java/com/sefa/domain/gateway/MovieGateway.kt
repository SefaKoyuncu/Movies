package com.sefa.domain.gateway

import androidx.paging.PagingData
import com.sefa.domain.model.SingleMovie
import kotlinx.coroutines.flow.Flow

interface MovieGateway
{
    suspend fun getPopularMovies() : Flow<PagingData<SingleMovie>>
    suspend fun upsertMovie(movie: SingleMovie)
    suspend fun deleteMovie(movieID: Int)
    suspend fun isMovieExist(movieID: Int) : Flow<Boolean>
    suspend fun getFavMovies() : Flow<PagingData<SingleMovie>>
}




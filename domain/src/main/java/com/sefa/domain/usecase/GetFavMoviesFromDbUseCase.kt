package com.sefa.domain.usecase

import androidx.paging.PagingData
import com.sefa.domain.gateway.MovieGateway
import com.sefa.domain.model.SingleMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavMoviesFromDbUseCase
@Inject constructor(private val movieGateway: MovieGateway)
{
    suspend fun invoke() : Flow<PagingData<SingleMovie>> = flow {
       movieGateway.getFavMovies()
           .collect { list->
               emit(list)
           }
    }.flowOn(Dispatchers.IO)
}
package com.sefa.domain.usecase

import com.sefa.domain.gateway.MovieGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetIsMovieExistInDbUseCase
@Inject constructor(private val movieGateway: MovieGateway)
{
    suspend fun invoke(movieID: Int) : Flow<Boolean>
        = movieGateway.isMovieExist(movieID)
            .flowOn(Dispatchers.IO)
}
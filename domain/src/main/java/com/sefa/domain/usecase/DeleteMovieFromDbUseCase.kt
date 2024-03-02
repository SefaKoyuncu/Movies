package com.sefa.domain.usecase

import com.sefa.domain.gateway.MovieGateway
import javax.inject.Inject

class DeleteMovieFromDbUseCase
@Inject constructor(private val movieGateway: MovieGateway)
{
    suspend fun invoke(movieID: Int)
    {
        movieGateway.deleteMovie(movieID)
    }
}
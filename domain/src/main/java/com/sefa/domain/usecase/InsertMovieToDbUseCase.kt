package com.sefa.domain.usecase

import com.sefa.domain.gateway.MovieGateway
import com.sefa.domain.model.SingleMovie
import javax.inject.Inject

class InsertMovieToDbUseCase
@Inject constructor(private val movieGateway: MovieGateway)
{
    suspend fun invoke(movie: SingleMovie)
    {
        movieGateway.upsertMovie(movie)
    }
}
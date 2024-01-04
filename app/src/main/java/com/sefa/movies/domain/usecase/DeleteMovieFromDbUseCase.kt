package com.sefa.movies.domain.usecase

import com.sefa.movies.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteMovieFromDbUseCase
@Inject constructor(private val movieRepository: MovieRepository)
{
    suspend fun invoke(movieID: Int)
    {
        movieRepository.deleteFromDb(movieID=movieID)
    }
}
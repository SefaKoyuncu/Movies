package com.sefa.movies.domain.usecase

import com.sefa.movies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetIsMovieExistInDbUseCase
@Inject constructor(private val movieRepository: MovieRepository)
{
    fun invoke(movieID: Int): Flow<Boolean> =
        movieRepository.isMovieExistInDb(movieID)
            .flowOn(Dispatchers.IO)
}



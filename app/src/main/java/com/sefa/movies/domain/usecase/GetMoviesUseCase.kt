package com.sefa.movies.domain.usecase

import com.sefa.movies.data.util.Resource
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase
@Inject constructor(private val movieRepository: MovieRepository)
{
    suspend fun invoke(): Flow<Resource<List<Movie>>> =  movieRepository.fetchData()
}
package com.sefa.movies.domain.usecase

import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.repository.MovieRepository
import javax.inject.Inject

class InsertMovieToDbUseCase
@Inject constructor(private val movieRepository: MovieRepository)
{
    suspend fun invoke(movie: Movie)
    {
        movieRepository.insertToDb(movie=movie)
    }
}
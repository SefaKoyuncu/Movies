package com.sefa.movies.domain.usecase

import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.repository.MovieRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class InsertMovieToDbUseCaseTest
{
    @Test
    fun `invoke should call insertToDb in MovieRepository`() = runBlocking {

        // Given
        val movie = Movie(1,"title1","01.01.2001",1.1,"poster1","overview1")
        val movieRepository = mockk<MovieRepository>()
        val insertMovieToDbUseCase = InsertMovieToDbUseCase(movieRepository)
        coEvery { movieRepository.insertToDb(any()) } just Runs

        // When
        insertMovieToDbUseCase.invoke(movie = movie)

        // Then
        coVerify { movieRepository.insertToDb(movie = movie) }
    }
}
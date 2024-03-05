package com.sefa.domain.usecase

import com.sefa.data.repository.MovieRepository
import com.sefa.domain.DataPlaceholder
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
        val movie = DataPlaceholder.movie
        val movieRepository = mockk<MovieRepository>()
        val insertMovieToDbUseCase = InsertMovieToDbUseCase(movieRepository)
        coEvery { movieRepository.insertMovie(any()) } just Runs

        // When
        insertMovieToDbUseCase.invoke(movie = movie)

        // Then
        coVerify { movieRepository.insertMovie(movie = movie) }
    }
}
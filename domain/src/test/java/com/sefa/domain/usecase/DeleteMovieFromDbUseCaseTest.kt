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

class DeleteMovieFromDbUseCaseTest
{

    private val movie = DataPlaceholder.movie
    @Test
    fun `invoke should call deleteFromDb in MovieRepository`() = runBlocking {

        // Given
        val movieRepository = mockk<MovieRepository>()
        val deleteMovieFromDbUseCase = DeleteMovieFromDbUseCase(movieRepository)

        // When
        coEvery { movieRepository.deleteMovie(any()) } just Runs
        deleteMovieFromDbUseCase.invoke(movieID = movie.id)

        // Then
        coVerify { movieRepository.deleteMovie(movieID = movie.id) }
    }
}

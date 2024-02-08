package com.sefa.movies.domain.usecase

import com.sefa.movies.domain.repository.MovieRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteMovieFromDbUseCaseTest {

    @Test
    fun `invoke should call deleteFromDb in MovieRepository`() = runBlocking {

        // Given
        val movieRepository = mockk<MovieRepository>()
        val deleteMovieFromDbUseCase = DeleteMovieFromDbUseCase(movieRepository)

        // When
        coEvery { movieRepository.deleteFromDb(any()) } just Runs
        deleteMovieFromDbUseCase.invoke(movieID = 123)

        // Then
        coVerify { movieRepository.deleteFromDb(movieID = 123) }
    }
}

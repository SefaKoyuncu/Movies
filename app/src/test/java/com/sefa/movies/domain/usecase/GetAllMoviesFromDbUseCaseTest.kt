package com.sefa.movies.domain.usecase

import androidx.paging.PagingData
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAllMoviesFromDbUseCaseTest
{
    @Test
    fun `invoke should return Flow of PagingData`() = runBlocking {

        // Given
        val expectedMovies = listOf(
            Movie(id = 1, title = "Movie 1"),
            Movie(id = 2, title = "Movie 2"),
            Movie(id = 3, title = "Movie 3")
        )
        val expectedPagingData: PagingData<Movie> = PagingData.from(expectedMovies)
        val movieRepository = mockk<MovieRepository>()
        val getAllMoviesFromDbUseCase = GetAllMoviesFromDbUseCase(movieRepository)
        coEvery { movieRepository.getAllMoviesFromDb() } returns flow { emit(expectedPagingData) }

        // When
        val resultFlow: Flow<PagingData<Movie>> = getAllMoviesFromDbUseCase.invoke()

        // Then
        val resultPagingData: PagingData<Movie> = resultFlow.first()
        assertEquals(expectedPagingData, resultPagingData)
    }
}
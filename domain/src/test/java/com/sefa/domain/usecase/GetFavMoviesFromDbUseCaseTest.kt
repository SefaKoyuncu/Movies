package com.sefa.domain.usecase

import androidx.paging.PagingData
import com.sefa.data.repository.MovieRepository
import com.sefa.domain.DataPlaceholder
import com.sefa.domain.model.SingleMovie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetFavMoviesFromDbUseCaseTest
{
    @Test
    fun `invoke should return Flow of PagingData`() = runBlocking {

        // Given
        val expectedMovies = DataPlaceholder.movieList
        val expectedPagingData: PagingData<SingleMovie> = PagingData.from(expectedMovies)
        val movieRepository = mockk<MovieRepository>()
        val getAllMoviesFromDbUseCase = GetFavMoviesFromDbUseCase(movieRepository)
        coEvery { movieRepository.getFavMovies() } returns flow { emit(expectedPagingData) }

        // When
        val resultFlow: Flow<PagingData<SingleMovie>> = getAllMoviesFromDbUseCase.invoke()

        // Then
        val resultPagingData: PagingData<SingleMovie> = resultFlow.first()
        assertEquals(expectedPagingData, resultPagingData)
    }
}
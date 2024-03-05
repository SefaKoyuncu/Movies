package com.sefa.domain.usecase

import androidx.paging.PagingData
import com.sefa.common_test.DataPlaceholder
import com.sefa.data.repository.MovieRepository
import com.sefa.domain.model.SingleMovie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMoviesFromAPIUseCaseTest
{

    @Test
    fun `invoke should return Flow of PagingData`() = runBlocking {

        // Given
        val expectedMovies = DataPlaceholder.movieList
        val expectedPagingData: PagingData<SingleMovie> = PagingData.from(expectedMovies)
        val movieRepository = mockk<MovieRepository>()
        val getMoviesFromAPIUseCase = GetMoviesFromAPIUseCase(movieRepository)
        coEvery { movieRepository.getPopularMovies() } returns flow { emit(expectedPagingData) }

        // When
        val resultFlow: Flow<PagingData<SingleMovie>> = getMoviesFromAPIUseCase.invoke()

        // Then
        val resultPagingData: PagingData<SingleMovie> = resultFlow.first()
        assertEquals(expectedPagingData, resultPagingData)
    }
}

package com.sefa.domain.usecase

import com.sefa.data.repository.MovieRepository
import com.sefa.domain.DataPlaceholder
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetIsMovieExistInDbUseCaseTest
{

    @Test
    fun `invoke should return Flow of Boolean`() = runBlocking {

        //Given
        val movie = DataPlaceholder.movie
        val movieRepository = mockk<MovieRepository>()
        val getIsMovieExistInDbUseCase = GetIsMovieExistInDbUseCase(movieRepository)

        coEvery { movieRepository.isMovieExist(movieID = movie.id) } returns flow { emit(true) }

        // When
        val resultFlow: Flow<Boolean> = getIsMovieExistInDbUseCase.invoke(movieID = movie.id)
        val resultBoolean: Boolean? = resultFlow.firstOrNull()

        // Then
        assertEquals(true, resultBoolean)
        coVerify { movieRepository.isMovieExist(movieID = movie.id) }
    }
}

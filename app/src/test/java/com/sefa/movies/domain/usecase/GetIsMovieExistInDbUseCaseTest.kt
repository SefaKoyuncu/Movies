package com.sefa.movies.domain.usecase
import com.sefa.movies.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetIsMovieExistInDbUseCaseTest {

    @Test
    fun `invoke should return Flow of Boolean`() = runBlocking {

        //Given
        val movieRepository = mockk<MovieRepository>()
        val getIsMovieExistInDbUseCase = GetIsMovieExistInDbUseCase(movieRepository)
        val sampleMovieID = 123
        coEvery { movieRepository.isMovieExistInDb(sampleMovieID) } returns flow { emit(true) }

        // When
        val resultFlow: Flow<Boolean> = getIsMovieExistInDbUseCase.invoke(sampleMovieID)
        val resultBoolean: Boolean? = resultFlow.firstOrNull()

        // Then
        assertEquals(true, resultBoolean)
        coVerify { movieRepository.isMovieExistInDb(sampleMovieID) }
    }
}

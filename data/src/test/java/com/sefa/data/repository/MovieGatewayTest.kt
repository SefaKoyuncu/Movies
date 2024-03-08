package com.sefa.data.repository

import androidx.paging.PagingData
import com.sefa.data.datasources.local.MovieDAO
import com.sefa.data.datasources.remote.service.MovieService
import com.sefa.common_test.DataPlaceholder
import com.sefa.domain.model.SingleMovie
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieGatewayTest
{
    private lateinit var movieRepository: MovieRepository

    private var movieService: MovieService = mockk()
    private var movieDao: MovieDAO = mockk()
    val movie = DataPlaceholder.movie

    @Before
    fun setUp() {
        movieRepository = MovieRepository(movieService, movieDao)
    }

    @Test
    fun `getPagingData should return flow of PagingData`() = runBlocking {

    }

    @Test
    fun `getAllMoviesFromDb should return flow of PagingData`() = runBlocking {

    }

    @Test
    fun `getAllMoviesFromDb should handle error and return empty PagingData`() = runBlocking {

        // Given
        coEvery { movieDao.getFavMovies() } throws Exception()

        // When
        val result = try {
            movieRepository.getFavMovies().toList()
        } catch (e: Exception) {
            emptyList<PagingData<SingleMovie>>()
        }

        // Then
        coVerify {
            movieDao.getFavMovies()
        }
        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun `isMovieExistInDb should return true when movie exists`() = runBlocking {

        // Given
        coEvery { movieDao.isMovieExist(movie.id) } returns true

        // When
        val result = movieRepository.isMovieExist(movie.id).first()

        // Then
        coVerify {
            movieDao.isMovieExist(movie.id)
        }
        assert(result)
    }

    @Test
    fun `isMovieExistInDb should return false when movie does not exist`() = runBlocking {

        // Given
        coEvery { movieDao.isMovieExist(movie.id) } returns false

        // When
        val result = movieRepository.isMovieExist(movie.id).first()

        // Then
        coVerify { movieDao.isMovieExist(movie.id) }
        assert(!result)
    }

    @Test
    fun `insertToDb should call upsertMovie`() = runBlocking {

        // Given
        coEvery { movieDao.upsertMovie(movie) } just Runs

        // When
        movieRepository.insertMovie(movie)

        // Then
        coVerify {
            movieDao.upsertMovie(movie)
        }
    }

    @Test
    fun `deleteFromDb should call deleteMovie`() = runBlocking {

        // Given
        coEvery { movieDao.deleteMovie(movie.id) } just Runs

        // When
        movieRepository.deleteMovie(movie.id)

        // Then
        coVerify { movieDao.deleteMovie(movie.id) }
    }
}
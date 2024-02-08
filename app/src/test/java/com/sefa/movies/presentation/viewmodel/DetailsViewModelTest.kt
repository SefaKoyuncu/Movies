package com.sefa.movies.presentation.viewmodel

import app.cash.turbine.test
import com.sefa.movies.MainDispatcherCoroutineRule
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.usecase.DeleteMovieFromDbUseCase
import com.sefa.movies.domain.usecase.GetIsMovieExistInDbUseCase
import com.sefa.movies.domain.usecase.InsertMovieToDbUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest
{

    @get:Rule
    var mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    private lateinit var viewModel: DetailsViewModel
    private lateinit var getIsMovieExistInDb: GetIsMovieExistInDbUseCase
    private lateinit var insertMovieToDbUseCase: InsertMovieToDbUseCase
    private lateinit var deleteMovieFromDbUseCase: DeleteMovieFromDbUseCase

    @Before
    fun setup() {
        getIsMovieExistInDb = mockk()
        insertMovieToDbUseCase = mockk()
        deleteMovieFromDbUseCase = mockk()

        viewModel = DetailsViewModel(
            getIsMovieExistInDb,
            insertMovieToDbUseCase,
            deleteMovieFromDbUseCase
        )
    }

    @Test
    fun `checkIfMovieExists should update isMovieExistInDb_`() = runBlocking {

        // Given
        val movieId = 123
        val isMovieExistInDbFlow = flowOf(true) // Simulated flow
        coEvery { getIsMovieExistInDb.invoke(movieID = movieId) } returns isMovieExistInDbFlow

        // When
        viewModel.checkIfMovieExists(movieId)

        // Then
        viewModel.getIsMovieExistInDb_.test {
            assertEquals(awaitItem(), true)
        }
    }

    @Test
    fun `insertMovieDb should call insertMovieToDbUseCase`() = runBlocking {
        // Given
        val movie = Movie(1, "Test Movie1")

        // When
        viewModel.insertMovieDb(movie)

        // Then
        coVerify { insertMovieToDbUseCase.invoke(movie) }
    }

    @Test
    fun `deleteFromDb should call deleteMovieFromDbUseCase`() = runBlocking {
        // Given
        val movieId = 123

        // When
        viewModel.deleteFromDb(movieId)

        // Then
        coVerify { deleteMovieFromDbUseCase.invoke(movieID = movieId) }
    }
}
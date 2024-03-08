package com.sefa.feature_details.viewmodel

import app.cash.turbine.test
import com.sefa.common_test.DataPlaceholder
import com.sefa.common_test.MainDispatcherCoroutineRule
import com.sefa.domain.usecase.DeleteMovieFromDbUseCase
import com.sefa.domain.usecase.GetIsMovieExistInDbUseCase
import com.sefa.domain.usecase.InsertMovieToDbUseCase
import com.sefa.feature_details.DetailsViewModel
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
    private val movie = DataPlaceholder.movie

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
        val isMovieExistInDbFlow = flowOf(true) // Simulated flow
        coEvery { getIsMovieExistInDb.invoke(movieID = movie.id) } returns isMovieExistInDbFlow

        // When
        viewModel.checkIfMovieExists(movieID = movie.id)

        // Then
        viewModel.getIsMovieExistInDb_.test {
            assertEquals(awaitItem(), true)
        }
    }

    @Test
    fun `insertMovieDb should call insertMovieToDbUseCase`() = runBlocking {

        // Given

        // When
        viewModel.insertMovieDb(movie)

        // Then
        coVerify { insertMovieToDbUseCase.invoke(movie) }
    }

    @Test
    fun `deleteFromDb should call deleteMovieFromDbUseCase`() = runBlocking {

        // Given

        // When
        viewModel.deleteFromDb(movieID = movie.id)

        // Then
        coVerify { deleteMovieFromDbUseCase.invoke(movieID = movie.id) }
    }
}
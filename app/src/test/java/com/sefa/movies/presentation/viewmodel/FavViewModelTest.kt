package com.sefa.movies.presentation.viewmodel

import androidx.paging.PagingData
import app.cash.turbine.test
import com.sefa.movies.MainDispatcherCoroutineRule
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.usecase.GetAllMoviesFromDbUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavViewModelTest
{
    @get:Rule
    var mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @Mock
    private lateinit var getAllMoviesFromDbUseCase: GetAllMoviesFromDbUseCase

    @Test
    fun `observeMovies should update moviesPagingData_`()
    {
        runTest {
            val expectedResult : PagingData<Movie> = PagingData.from(listOf(Movie(1,"Test 1"), Movie(2,"Test 2")))
            val viewModel = FavViewModel(getAllMoviesFromDbUseCase)
            doReturn(flowOf(expectedResult)).`when`(getAllMoviesFromDbUseCase).invoke()
            viewModel.getMoviesPagingData.test {
                assertEquals(expectedResult, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
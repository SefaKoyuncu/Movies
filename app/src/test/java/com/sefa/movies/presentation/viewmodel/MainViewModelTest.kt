package com.sefa.movies.presentation.viewmodel

import androidx.paging.PagingData
import app.cash.turbine.test
import com.sefa.movies.MainDispatcherCoroutineRule
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.usecase.GetMoviesFromAPIUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest
{
    @get:Rule
    var mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    @Mock
    private lateinit var getMoviesFromAPIUseCase: GetMoviesFromAPIUseCase

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess()
    {
        runTest {
            val expectedResult : PagingData<Movie> = PagingData.from(listOf(Movie(1,"Test 1"), Movie(2,"Test 2")))
            doReturn(flowOf(expectedResult)).`when`(getMoviesFromAPIUseCase).invoke()
            val viewModel = MainViewModel(getMoviesFromAPIUseCase)
            viewModel.getMoviesPagingData.test {
                assertEquals(expectedResult, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(getMoviesFromAPIUseCase).invoke()
        }
    }
}

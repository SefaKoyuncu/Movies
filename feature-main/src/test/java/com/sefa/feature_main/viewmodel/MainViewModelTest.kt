package com.sefa.feature_main.viewmodel

import androidx.paging.PagingData
import app.cash.turbine.test
import com.sefa.common_test.DataPlaceholder
import com.sefa.common_test.MainDispatcherCoroutineRule
import com.sefa.domain.model.SingleMovie
import com.sefa.domain.usecase.GetMoviesFromAPIUseCase
import com.sefa.feature_main.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest
{
    @get:Rule
    var mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    private var getMoviesFromAPIUseCase: GetMoviesFromAPIUseCase = mockk()

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess()
    {
        runTest {
            val viewModel = MainViewModel(getMoviesFromAPIUseCase)

            val movieList = DataPlaceholder.movieList
            val expectedResult : PagingData<SingleMovie> = PagingData.from(movieList)

            coEvery { getMoviesFromAPIUseCase.invoke() } returns flowOf(expectedResult)

            viewModel.getMoviesPagingData.test {
                assertEquals(expectedResult, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(getMoviesFromAPIUseCase).invoke()
        }
    }
}

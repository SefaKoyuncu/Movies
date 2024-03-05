package com.sefa.feature_fav.viewmodel

import androidx.paging.PagingData
import app.cash.turbine.test
import com.sefa.common_test.DataPlaceholder
import com.sefa.common_test.MainDispatcherCoroutineRule
import com.sefa.domain.model.SingleMovie
import com.sefa.domain.usecase.GetFavMoviesFromDbUseCase
import com.sefa.feature_fav.FavViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavViewModelTest
{
    @get:Rule
    var mainDispatcherCoroutineRule = MainDispatcherCoroutineRule()

    private var getFavMoviesFromDbUseCase: GetFavMoviesFromDbUseCase = mockk()

    @Test
    fun `givenServerResponse200_whenFetch_shouldReturnSuccess`()
    {
        runTest {
            val viewModel = FavViewModel(getFavMoviesFromDbUseCase)

            // Given
            val movieList = DataPlaceholder.movieList
            val expectedPagingData: PagingData<SingleMovie> = PagingData.from(movieList)

            coEvery { getFavMoviesFromDbUseCase.invoke() } returns flowOf(expectedPagingData)

            // Then
            viewModel.getMoviesPagingData.test {
                assertEquals(expectedPagingData, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(getFavMoviesFromDbUseCase).invoke()
        }
    }
}
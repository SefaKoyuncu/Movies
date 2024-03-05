package com.sefa.data.datasources.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sefa.data.util.Constants.DEFAULT_PAGE_INDEX
import com.sefa.data.datasources.remote.service.MovieService
import com.sefa.data.mapper.asMovies
import com.sefa.domain.model.SingleMovie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemotePagingSource
@Inject
constructor(private val movieService: MovieService) : PagingSource<Int,SingleMovie>()
{

    override fun getRefreshKey(state: PagingState<Int, SingleMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SingleMovie> {
         return try {
            val nextPageNumber = params.key ?: DEFAULT_PAGE_INDEX
            val response = movieService.getPopularMovies(page = nextPageNumber)
            if (response.isSuccessful)
            {
                if (response.body()?.results.isNullOrEmpty())
                {
                    return LoadResult.Error(Exception("Ooops, error loading data!"))
                }

                val results = response.asMovies().body()?.results ?: emptyList()

                LoadResult.Page(
                    data = results,
                    prevKey = if (nextPageNumber == DEFAULT_PAGE_INDEX) null else nextPageNumber - 1,
                    nextKey = if (results.isEmpty()) null else nextPageNumber + 1
                )
            }
            else
            {
                LoadResult.Error(Exception("Ooops, error loading data!"))
            }

        } catch (exception: IOException) {
            return LoadResult.Error(Exception("No internet connection!"))
        } catch (exception: HttpException) {
            return LoadResult.Error(Exception("Server connection failed!"))
        }
    }
}
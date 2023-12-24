package com.sefa.movies.data.datasources.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sefa.movies.data.datasources.remote.service.MovieService
import com.sefa.movies.data.mapper.MovieMapper
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.utils.Constants.DEFAULT_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviesPagingSource
@Inject
constructor(private val movieService: MovieService) : PagingSource<Int,Movie>()
{
    @Inject
    lateinit var movieMapper: MovieMapper

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
         return try {
            val nextPageNumber = params.key ?: DEFAULT_PAGE_INDEX
            val response = movieService.getPopularMovies(page = nextPageNumber)
            if (response.isSuccessful)
            {
                val results = response.body()?.results
                movieMapper = MovieMapper()
                val movies = results?.let { movieMapper.mapMovieResponseToDomain(it) } ?: emptyList()

                LoadResult.Page(
                    data = movies,
                    prevKey = if (nextPageNumber == DEFAULT_PAGE_INDEX) null else nextPageNumber - 1,
                    nextKey = if (movies.isEmpty()) null else nextPageNumber + 1
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
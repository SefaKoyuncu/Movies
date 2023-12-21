package com.sefa.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sefa.movies.data.datasources.remote.datasource.MoviesPagingSource
import com.sefa.movies.data.datasources.remote.service.MovieService
import com.sefa.movies.data.util.Resource
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.repository.MovieRepository
import com.sefa.movies.utils.Constants.DEFAULT_PAGE_SIZE
import com.sefa.movies.utils.Constants.PREFETCH_DISTANCE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import okhttp3.internal.wait
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(private val movieService: MovieService)
    : MovieRepository
{
    override suspend fun getPagingData(): Flow<Resource<PagingData<Movie>>>
    {
        return Pager(
                    config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE,prefetchDistance = PREFETCH_DISTANCE),
                    pagingSourceFactory = { MoviesPagingSource(movieService = movieService) }
        ).flow
            //.cachedIn(scope)
            .map { pagingData -> Resource.Success(pagingData) }
            .flowOn(Dispatchers.IO)
            /*.catch {
                Resource.Error<String>(it.message.toString())
            }*/
    }
}
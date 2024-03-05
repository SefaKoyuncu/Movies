package com.sefa.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sefa.data.util.Constants.DEFAULT_PAGE_SIZE
import com.sefa.data.util.Constants.PREFETCH_DISTANCE
import com.sefa.data.datasources.local.MovieDAO
import com.sefa.data.datasources.remote.datasource.RemotePagingSource
import com.sefa.data.datasources.remote.service.MovieService
import com.sefa.domain.gateway.MovieGateway
import com.sefa.domain.model.SingleMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository
@Inject constructor ( private val movieService: MovieService,
                      private val movieDAO: MovieDAO
                      ) : MovieGateway
{
    override suspend fun getPopularMovies() : Flow<PagingData<SingleMovie>>
    {
        return Pager(
            config = PagingConfig( pageSize = DEFAULT_PAGE_SIZE,prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = { RemotePagingSource(movieService = movieService) }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    override suspend fun insertMovie(movie: SingleMovie) = movieDAO.upsertMovie(movie)

    override suspend fun deleteMovie(movieID: Int) = movieDAO.deleteMovie(movieID)

    override suspend fun isMovieExist(movieID: Int) : Flow<Boolean>
    {
        return flow {
            emit(movieDAO.isMovieExist(movieID))
        }
    }

    override suspend fun getFavMovies() : Flow<PagingData<SingleMovie>>
    {
        return Pager(
            config = PagingConfig( pageSize = DEFAULT_PAGE_SIZE,prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = {movieDAO.getFavMovies()}
        ).flow
            .flowOn(Dispatchers.IO)
    }
}
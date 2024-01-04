package com.sefa.movies.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sefa.movies.data.datasources.local.MovieDAO
import com.sefa.movies.data.datasources.remote.datasource.MoviesPagingSource
import com.sefa.movies.data.datasources.remote.service.MovieService
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.repository.MovieRepository
import com.sefa.movies.utils.Constants.DEFAULT_PAGE_SIZE
import com.sefa.movies.utils.Constants.PREFETCH_DISTANCE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(private val movieService: MovieService, private val movieDAO: MovieDAO)
    : MovieRepository
{
    override fun getPagingData(): Flow<PagingData<Movie>>
    {
        return Pager(
                    config = PagingConfig( pageSize = DEFAULT_PAGE_SIZE,prefetchDistance = PREFETCH_DISTANCE),
                    pagingSourceFactory = { MoviesPagingSource(movieService = movieService) }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    override fun isMovieExistInDb(movieID: Int): Flow<Boolean>
    {
        return flow {
            emit(movieDAO.isMovieExist(movieID = movieID))
            Log.e("TAG","repo:"+movieDAO.isMovieExist(movieID = movieID).toString())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertToDb(movie: Movie)
    {
        movieDAO.upsertMovie(movie)
    }

    override suspend fun deleteFromDb(movieID: Int)
    {
        movieDAO.deleteMovie(movieID=movieID)
    }

    override suspend fun getAllMoviesFromDb(): Flow<PagingData<Movie>>
    {
        return Pager(
            config = PagingConfig( pageSize = DEFAULT_PAGE_SIZE,prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = {movieDAO.getAllMovies()}
        ).flow
            .flowOn(Dispatchers.IO)
    }
}
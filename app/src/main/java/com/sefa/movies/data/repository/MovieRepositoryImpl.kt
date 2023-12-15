package com.sefa.movies.data.repository

import com.sefa.movies.data.datasources.remote.datasource.RemoteDataSource
import com.sefa.movies.data.mapper.MovieMapper
import com.sefa.movies.data.util.Resource
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(private val remoteDataSource: RemoteDataSource, private val movieMapper: MovieMapper)
    : MovieRepository
{
    override suspend fun fetchData(): Flow<Resource<List<Movie>>>
    {
        return flow {
            emit(Resource.Loading())

            try
            {
                val response = remoteDataSource.getPopularMovies()
                if (response.isSuccessful)
                {
                    val movies = response.body()?.results
                    movies?.let {
                        emit(Resource.Success(movieMapper.mapMovieResponseToDomain(it)))
                    }
                }
            }
            catch (e : Exception)
            {
                emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
            }

        }.flowOn(Dispatchers.IO)
    }
}
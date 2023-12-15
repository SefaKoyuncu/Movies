package com.sefa.movies.data.datasources.remote.datasource

import com.sefa.movies.data.datasources.remote.service.MovieService
import javax.inject.Inject

class RemoteDataSource
@Inject
constructor(private val movieService: MovieService)
{
    suspend fun getPopularMovies()=movieService.getPopularMovies(page=1)
}
package com.sefa.movies.data.datasources.remote

import javax.inject.Inject

class RemoteDataSources
@Inject
constructor(private val movieService: MovieService)
{
    suspend fun getPopularMovies()=movieService.getPopularMovies(page=1)
}
package com.sefa.movies.data.datasources.remote.service

import com.sefa.movies.data.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<MoviesResponse>
}

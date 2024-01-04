package com.sefa.movies.domain.repository

import androidx.paging.PagingData
import com.sefa.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository
{
    fun getPagingData() : Flow<PagingData<Movie>>

    fun isMovieExistInDb(movieID: Int) : Flow<Boolean>

    suspend fun insertToDb(movie: Movie)

    suspend fun deleteFromDb(movieID: Int)

    suspend fun getAllMoviesFromDb() : Flow<PagingData<Movie>>
}
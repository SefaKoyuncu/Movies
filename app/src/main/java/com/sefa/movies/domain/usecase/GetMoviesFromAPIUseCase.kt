package com.sefa.movies.domain.usecase

import androidx.paging.PagingData
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMoviesFromAPIUseCase
@Inject constructor(private val movieRepository: MovieRepository) {
    suspend fun invoke(): Flow<PagingData<Movie>> = flow {
            movieRepository.getPagingData()
                .collect {list->
                    emit(list)
                }
    }.flowOn(Dispatchers.IO)
}
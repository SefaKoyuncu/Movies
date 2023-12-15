package com.sefa.movies.data.mapper

import com.sefa.movies.data.model.Result
import com.sefa.movies.domain.model.Movie

class MovieMapper
{
    fun mapMovieResponseToDomain(results: List<Result>): List<Movie> {
        return results.map { mapMovieResultToDomain(it) }
    }

    fun mapMovieResultToDomain(result : Result): Movie {
        return Movie(
            id = result.id,
            title = result.title,
            release_date = result.release_date,
            vote_average=result.vote_average,
            poster_path = result.poster_path,
            overview = result.overview
        )
    }
}




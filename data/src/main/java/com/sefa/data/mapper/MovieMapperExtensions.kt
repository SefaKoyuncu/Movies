package com.sefa.data.mapper

import com.sefa.data.model.MoviesResponse
import com.sefa.data.model.Result
import com.sefa.domain.model.Movies
import com.sefa.domain.model.SingleMovie
import retrofit2.Response

fun Response<MoviesResponse>.asMovies(): Response<Movies> = Response.success(body()?.let {
    Movies(
        page = it.page,
        results = it.results.map { movie-> movie.asSingleMovie() },
        total_pages = it.total_pages,
        total_results = it.total_results
    )
})

fun Result.asSingleMovie() = SingleMovie(
    id = id,
    title = title,
    release_date = release_date,
    vote_average = vote_average,
    poster_path = poster_path,
    overview = overview
)

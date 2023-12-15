package com.sefa.movies.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String,
    val overview: String,
    )
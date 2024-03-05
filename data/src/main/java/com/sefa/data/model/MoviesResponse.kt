package com.sefa.data.model

import androidx.annotation.Keep

@Keep
data class MoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
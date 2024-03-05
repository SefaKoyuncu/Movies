package com.sefa.data.util

import com.sefa.domain.model.Movies
import com.sefa.domain.model.SingleMovie

object DataPlaceholder
{
    val movieList = listOf(
        SingleMovie(id = 1, title = "Madame Web", overview = "Forced to confront revelations about her past, paramedic Cassandra Webb forges a relationship with three young women destined for powerful futures...if they can all survive a deadly present.", poster_path = "/rULWuutDcN5NvtiZi4FRPzRYWSh.jpg", vote_average = 5.441)
    )

    val movie = SingleMovie(id = 1, title = "Madame Web", overview = "Forced to confront revelations about her past, paramedic Cassandra Webb forges a relationship with three young women destined for powerful futures...if they can all survive a deadly present.", poster_path = "/rULWuutDcN5NvtiZi4FRPzRYWSh.jpg", vote_average = 5.441)

    val movieResponse = Movies(
        page = 1,
        results = movieList,
        total_pages = 1,
        total_results = 1
    )
}
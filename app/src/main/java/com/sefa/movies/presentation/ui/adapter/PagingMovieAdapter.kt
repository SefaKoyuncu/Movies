package com.sefa.movies.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sefa.movies.databinding.CardMovieBinding
import com.sefa.movies.domain.model.Movie
import com.sefa.movies.utils.Constants
import com.sefa.movies.utils.Constants.BASE_IMAGE_URL
import java.text.DecimalFormat

class PagingMovieAdapter(private val onItemClicked: (Movie) -> Unit) : PagingDataAdapter<Movie, PagingMovieAdapter.MovieViewHolder>(MOVIE_COMPARATOR)
{
    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    inner class MovieViewHolder(val binding: CardMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int)
    {
        lateinit var movie : Movie
        getItem(position)?.let {movie=it}

        val decimalFormat = DecimalFormat("#.#")

        val truncatedText = if (movie.title.length > 15) {
            "${movie.title.substring(0, 15)}..."
        } else {
            movie.title
        }

        holder.binding.apply {
            textViewMovieName.text = truncatedText
            textViewReleasedDate.text = movie.release_date
            textViewStarNumber.text = decimalFormat.format(movie.vote_average)
            imageViewMovie.load(BASE_IMAGE_URL + movie.poster_path)

           /* imageViewFavIcon.setOnClickListener {
                onItemClicked(movie)
            }*/

            root.setOnClickListener{
                onItemClicked(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder
    {
        return MovieViewHolder(
            CardMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Movie)
    }
}
package com.sefa.common_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sefa.common_ui.databinding.CardMovieBinding
import com.sefa.domain.model.SingleMovie
import java.text.DecimalFormat

class PagingMovieAdapter(private val onItemClicked: (SingleMovie) -> Unit) : PagingDataAdapter<SingleMovie, PagingMovieAdapter.MovieViewHolder>(MOVIE_COMPARATOR)
{
    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<SingleMovie>() {
            override fun areItemsTheSame(oldItem: SingleMovie, newItem: SingleMovie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SingleMovie, newItem: SingleMovie): Boolean =
                oldItem == newItem
        }
    }

    inner class MovieViewHolder(val binding: CardMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int)
    {
        lateinit var movie : SingleMovie
        getItem(position)?.let {movie=it}

        val decimalFormat = DecimalFormat("#.#")

        val truncatedText = if (movie.title.length > 16) {
            "${movie.title.substring(0, 16)}..."
        } else {
            movie.title
        }

        holder.binding.apply {
            textViewMovieName.text = truncatedText
            textViewReleasedDate.text = movie.release_date
            textViewStarNumber.text = decimalFormat.format(movie.vote_average)
            imageViewMovie.load(movie.poster_url)

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
        fun onItemSelected(position: Int, item: SingleMovie)
    }
}
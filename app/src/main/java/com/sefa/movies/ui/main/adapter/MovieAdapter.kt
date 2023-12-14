package com.sefa.movies.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sefa.movies.data.model.Result
import com.sefa.movies.databinding.CardMovieBinding
import com.sefa.movies.utils.Constants.BASE_IMAGE_URL
import java.text.DecimalFormat

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.CardViewHolder>()
{
    inner class CardViewHolder(val binding: CardMovieBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Result>(){
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list : List<Result>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        return CardViewHolder(
            CardMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        val movieResult = differ.currentList[position]
        val decimalFormat = DecimalFormat("#.#")

        val truncatedText = if (movieResult.title.length > 16) {
            "${movieResult.title.substring(0, 16)}..."
        } else {
            movieResult.title
        }

        holder.binding.apply {
            textViewMovieName.text = truncatedText
            textViewReleasedDate.text = movieResult.release_date
            textViewStarNumber.text = decimalFormat.format(movieResult.vote_average)
            imageViewMovie.load(BASE_IMAGE_URL+movieResult.poster_path)




           /* cardViewMovie.setOnClickListener{view->
                val nav : NavDirections = MainFragmentDirections.fromMaintoDetails(movieResult)
                Navigation.findNavController(view).navigate(nav)
            }*/
        }

    }
}
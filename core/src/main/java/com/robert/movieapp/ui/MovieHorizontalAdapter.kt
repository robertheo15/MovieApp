package com.robert.movieapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robert.movieapp.core.databinding.ItemMovieHorizontalBinding
import com.robert.movieapp.domain.model.Movie
import com.robert.movieapp.utils.getImageUrl
import com.robert.movieapp.utils.setMovieRating
import com.robert.movieapp.utils.setImageFromUrl

class MovieHorizontalAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieHorizontalAdapter.ViewHolder>() {

    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieHorizontalBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        val context = holder.itemView.context

        holder.bind(context, movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(private var binding: ItemMovieHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, movie: Movie) {
            val rating = movie.voteAverage.div(2)
            binding.apply {
                ivMoviePoster.setImageFromUrl(
                    context,
                    getImageUrl(movie.posterPath)
                )
                tvMovieItemTitle.text = movie.title
                tvMovieItemRating.text = String.format("%.2f", rating)
                ivMovieRating.setMovieRating(rating.toInt())
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(movies[adapterPosition])
            }
        }
    }
}

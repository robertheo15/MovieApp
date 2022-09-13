package com.robert.movieapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.robert.movieapp.core.databinding.ItemMovieCastBinding
import com.robert.movieapp.domain.model.Cast
import com.robert.movieapp.utils.getImageOriginalUrl
import com.robert.movieapp.utils.setImageFromUrl

class MovieCastAdapter(private val casts: List<Cast>) :
    RecyclerView.Adapter<MovieCastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieCastBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cast = casts[position]
        val context = holder.itemView.context

        holder.bind(context, cast)
    }

    override fun getItemCount() = casts.size

    inner class ViewHolder(private var binding: ItemMovieCastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, cast: Cast) {
            binding.apply {
                ivCastProfile.setImageFromUrl(
                    context,
                    getImageOriginalUrl(cast.profilePath)
                )
                tvCastName.text = cast.name
                tvCastRole.text = cast.character
            }
        }
    }
}

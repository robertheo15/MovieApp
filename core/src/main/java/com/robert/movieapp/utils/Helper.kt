package com.robert.movieapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.robert.movieapp.core.BuildConfig.API_IMG_ENDPOINT
import com.robert.movieapp.core.BuildConfig.API_IMG_ORIGINAL_ENDPOINT
import com.robert.movieapp.core.R

fun getImageOriginalUrl(path: String?): String =
    "$API_IMG_ORIGINAL_ENDPOINT$path"

fun getImageUrl(path: String?): String =
    "$API_IMG_ENDPOINT$path"

fun ImageView.setImageFromUrl(context: Context, url: String) {
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.image_load_placeholder)
        .error(R.drawable.image_load_error)
        .into(this)
}

fun ImageView.setMovieRating(rating: Int) {
    when (rating) {
        0 -> this.setImageResource(R.drawable.ic_rating_0)
        1 -> this.setImageResource(R.drawable.ic_rating_1)
        2 -> this.setImageResource(R.drawable.ic_rating_2)
        3 -> this.setImageResource(R.drawable.ic_rating_3)
        4 -> this.setImageResource(R.drawable.ic_rating_4)
        5 -> this.setImageResource(R.drawable.ic_rating_5)
        else -> throw IllegalArgumentException("Invalid rating argument")
    }
}

package com.robert.movieapp.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.robert.movieapp.R
import com.robert.movieapp.databinding.ActivityDetailBinding
import com.robert.movieapp.domain.model.Movie
import com.robert.movieapp.ui.MovieCastAdapter
import com.robert.movieapp.ui.MovieHorizontalAdapter
import com.robert.movieapp.utils.Event
import com.robert.movieapp.utils.getImageOriginalUrl
import com.robert.movieapp.utils.setImageFromUrl
import com.robert.movieapp.utils.setMovieRating
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val movie: Movie = intent.getParcelableExtra(extraData)!!
        getDetailMovie(movie)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getDetailMovie(movie: Movie) {
        val rating = movie.voteAverage.div(2)
        var statusFavorite = movie.isFavorite

        binding.apply {
            ivMoviePoster.setImageFromUrl(
                this@DetailActivity,
                getImageOriginalUrl(movie.posterPath)
            )
            tvMovieTitle.text = movie.title
            ivMovieRating.setMovieRating(rating.toInt())
            tvMovieRating.text = String.format("%.2f", rating)
            tvMovieOverview.text = movie.overview
            setStatusFavorite(statusFavorite)
            toggleFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                detailViewModel.createMovieAsFavorite(movie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
        detailViewModel.snackBarText.observe(this) {
            showSnackBar(it)
        }
        getRecommendationMovies(movie.id)
        getMovieCasts(movie.id)
    }

    private fun getRecommendationMovies(id: Int) {
        detailViewModel.findRecommendationMovies(id).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isEmpty()) {
                    binding.apply {
                        rvRecommendationMovies.visibility = View.GONE
                        tvRecommendationMessage.visibility = View.VISIBLE
                    }
                } else {
                    val adapter = MovieHorizontalAdapter(movies)
                    adapter.onItemClick = { selectedData ->
                        val intent =
                            Intent(this@DetailActivity, DetailActivity::class.java)
                        intent.putExtra(extraData, selectedData)
                        startActivity(intent)
                    }

                    val layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    FlexboxLayoutManager(this)
                    val recyclerView = binding.rvRecommendationMovies
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }
                }
            }
            result.onFailure {
                Log.e("detail activity", "error")
            }
        }
    }

    private fun getMovieCasts(id: Int) {
        detailViewModel.findMovieCasts(id).observe(this) { result ->
            result.onSuccess { casts ->
                if (casts.isEmpty()) {
                    binding.apply {
                        rvMovieCast.visibility = View.GONE
                        tvRecommendationMessage.visibility = View.VISIBLE
                    }
                } else {
                    val adapter = MovieCastAdapter(casts)
                    val layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    FlexboxLayoutManager(this)
                    val recyclerView = binding.rvMovieCast
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }
                }
            }
            result.onFailure {
                Log.e("detail activity", "error")
            }
        }
    }

    private fun showSnackBar(eventMessage: Event<Int>) {
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            binding.constraintLayoutDetail,
            getString(message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.toggleFavorite.background =
                AppCompatResources.getDrawable(this, R.drawable.ic_favorite_24)
        } else {
            binding.toggleFavorite.background =
                AppCompatResources.getDrawable(this, R.drawable.ic_favorite_border_24)
        }
    }

    companion object {
        const val extraData = "extraData"
    }
}

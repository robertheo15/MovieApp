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
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        movie = (intent.getParcelableExtra(extraData) ?: return)
        getDetailMovie()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getDetailMovie() {
        if (detailViewModel.hasSetMovie.value == false) detailViewModel.setMovie(movie)
        detailViewModel.movie.observe(this) { movieLiveData ->

            detailViewModel.isFavorite.observe(this@DetailActivity) { isFavorite ->
                if (isFavorite) {
                    binding.toggleFavorite.background =
                        AppCompatResources.getDrawable(
                            this@DetailActivity,
                            R.drawable.ic_favorite
                        )
                } else {
                    binding.toggleFavorite.background =
                        AppCompatResources.getDrawable(
                            this@DetailActivity,
                            R.drawable.ic_favorite_border
                        )
                }
                binding.toggleFavorite.setOnClickListener {
                    detailViewModel.createMovieAsFavorite(movieLiveData, !isFavorite)
                }
            }

            val rating = movieLiveData.voteAverage.div(2)

            detailViewModel.snackBarText.observe(this) {
                showSnackBar(it)
            }

            binding.apply {
                ivMoviePoster.setImageFromUrl(
                    this@DetailActivity,
                    getImageOriginalUrl(movieLiveData.posterPath)
                )
                tvMovieTitle.text = movieLiveData.title
                ivMovieRating.setMovieRating(rating.toInt())
                tvMovieRating.text = String.format("%.2f", rating)
                tvMovieOverview.text = movieLiveData.overview
            }
            getRecommendationMovies(movieLiveData.id)
            getMovieCasts(movieLiveData.id)
        }
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

    companion object {
        const val extraData = "extraData"
    }
}

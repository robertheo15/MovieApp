package com.robert.movieapp.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.robert.movieapp.R
import com.robert.movieapp.data.Resource
import com.robert.movieapp.databinding.ActivityMainBinding
import com.robert.movieapp.ui.MovieHorizontalAdapter
import com.robert.movieapp.utils.getImageOriginalUrl
import com.robert.movieapp.utils.setImageFromUrl
import com.robert.movieapp.utils.setMovieRating
import com.robert.movieapp.view.detail.DetailActivity
import com.robert.movieapp.view.list.ListMovieActivity
import com.robert.movieapp.view.list.ListMovieActivity.Companion.extraListCategory
import com.robert.movieapp.view.search.SearchActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private var region: String = "US"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        getMovies()
        binding.apply {
            tvTrendingMore.setOnClickListener(this@MainActivity)
            tvPopularMore.setOnClickListener(this@MainActivity)
            tvUpComingMore.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvTrendingMore -> {
                startListMovieActivity("trending")
            }
            R.id.tvPopularMore -> {
                startListMovieActivity("popular")
            }
            R.id.tvUpComingMore -> {
                startListMovieActivity("upComing")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                Intent(this, SearchActivity::class.java).also { intent ->
                    startActivity(intent)
                }
                true
            }
            R.id.menu_favorite -> {
                val uri = Uri.parse("movieapp://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getMovies() {
        getMoviesNowPlaying()
        getTrendingMovies()
        getPopularMovies()
        getUpComingMovies()
    }

    private fun getMoviesNowPlaying() {
        mainViewModel.findMoviesNowPlaying(region).observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val end = (movies.data ?: return@observe).size - 1
                        var random = ((0 until end).random())
                        if (random < 0) random = 0
                        val movie = (movies.data ?: return@observe)[random]
                        binding.apply {
                            tvTitle.text = movie.title
                            tvMainRating.text = (movie.voteAverage.div(2)).toString()
                            tvInformation.text = movie.overview
                            ivmMainRating.setMovieRating((movie.voteAverage.div(2)).toInt())
                            movieBanner.setImageFromUrl(
                                this@MainActivity,
                                getImageOriginalUrl(movie.posterPath)
                            )
                            movieBanner.setOnClickListener {
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.extraData, movie)
                                startActivity(intent)
                            }
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    private fun getTrendingMovies() {
        mainViewModel.findTrendingMovies(region).observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val trendingAdapter = movies.data?.let { MovieHorizontalAdapter(it) }
                        if (trendingAdapter != null) {
                            trendingAdapter.onItemClick = { selectedData ->
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.extraData, selectedData)
                                startActivity(intent)
                            }
                        }

                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        FlexboxLayoutManager(this)
                        val recyclerView = binding.rvTrending
                        recyclerView.apply {
                            this.adapter = trendingAdapter
                            this.setHasFixedSize(true)
                            this.layoutManager = layoutManager
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    private fun getPopularMovies() {
        mainViewModel.findPopularMovies(region).observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val popularAdapter = movies.data?.let { MovieHorizontalAdapter(it) }
                        if (popularAdapter != null) {
                            popularAdapter.onItemClick = { selectedData ->
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.extraData, selectedData)
                                startActivity(intent)
                            }
                        }

                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        FlexboxLayoutManager(this)
                        val recyclerView = binding.rvPopular
                        recyclerView.apply {
                            this.adapter = popularAdapter
                            this.setHasFixedSize(true)
                            this.layoutManager = layoutManager
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    private fun getUpComingMovies() {
        mainViewModel.findUpComingMovies(region).observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val upComingAdapter = movies.data?.let { MovieHorizontalAdapter(it) }
                        if (upComingAdapter != null) {
                            upComingAdapter.onItemClick = { selectedData ->
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.extraData, selectedData)
                                startActivity(intent)
                            }
                        }

                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        FlexboxLayoutManager(this)
                        val recyclerView = binding.rvUpComing
                        recyclerView.apply {
                            this.adapter = upComingAdapter
                            this.setHasFixedSize(true)
                            this.layoutManager = layoutManager
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    private fun startListMovieActivity(category: String) {
        Intent(this, ListMovieActivity::class.java).also { intent ->
            intent.putExtra(extraListCategory, category)
            startActivity(intent)
        }
    }
}

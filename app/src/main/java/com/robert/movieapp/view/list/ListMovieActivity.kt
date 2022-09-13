package com.robert.movieapp.view.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.robert.movieapp.R
import com.robert.movieapp.data.Resource
import com.robert.movieapp.databinding.ActivityListMovieBinding
import com.robert.movieapp.ui.MovieVerticalAdapter
import com.robert.movieapp.view.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMovieBinding
    private val viewModel: ListMovieViewModel by viewModel()
    private var region: String = "US"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarListMovie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        when (intent.getStringExtra(extraListCategory)) {
            "trending" -> {
                getTrendingMovies()
            }
            "popular" -> {
                getPopularMovies()
            }
            "upComing" -> {
                getUpComingMovies()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getTrendingMovies() {
        supportActionBar?.title = getString(R.string.trending)
        viewModel.findTrendingMovies(region).observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val adapter = movies.data?.let { MovieVerticalAdapter(it) }
                        if (adapter != null) {
                            adapter.onItemClick = { selectedData ->
                                val intent =
                                    Intent(this@ListMovieActivity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.extraData, selectedData)
                                startActivity(intent)
                            }
                        }

                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        FlexboxLayoutManager(this)
                        val recyclerView = binding.rvListMovie
                        recyclerView.apply {
                            this.adapter = adapter
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
        supportActionBar?.title = getString(R.string.popular)
        viewModel.findPopularMovies(region).observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val adapter = movies.data?.let { MovieVerticalAdapter(it) }
                        if (adapter != null) {
                            adapter.onItemClick = { selectedData ->
                                val intent =
                                    Intent(this@ListMovieActivity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.extraData, selectedData)
                                startActivity(intent)
                            }
                        }

                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        FlexboxLayoutManager(this)
                        val recyclerView = binding.rvListMovie
                        recyclerView.apply {
                            this.adapter = adapter
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
        supportActionBar?.title = getString(R.string.upcoming)
        viewModel.findUpComingMovies(region).observe(this) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val adapter = movies.data?.let { MovieVerticalAdapter(it) }
                        if (adapter != null) {
                            adapter.onItemClick = { selectedData ->
                                val intent =
                                    Intent(this@ListMovieActivity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.extraData, selectedData)
                                startActivity(intent)
                            }
                        }

                        val layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        FlexboxLayoutManager(this)
                        val recyclerView = binding.rvListMovie
                        recyclerView.apply {
                            this.adapter = adapter
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

    companion object {
        const val extraListCategory = "extraListCategory"
    }
}

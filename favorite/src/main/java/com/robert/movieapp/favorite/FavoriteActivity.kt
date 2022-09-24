package com.robert.movieapp.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.robert.movieapp.R
import com.robert.movieapp.favorite.databinding.ActivityFavoriteBinding
import com.robert.movieapp.favorite.di.favoriteModule
import com.robert.movieapp.ui.MovieVerticalAdapter
import com.robert.movieapp.view.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFavorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        loadKoinModules(favoriteModule)
        getFavoriteData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getFavoriteData() {
        supportActionBar?.title = getString(R.string.favorite)
        favoriteViewModel.findFavoriteMovies().observe(this) { movies ->
            if (movies.isEmpty()) {
                binding.apply {
                    rvFavorite.visibility = View.GONE
                    tvMessage.visibility = View.VISIBLE
                    animationLottie.visibility = View.VISIBLE
                }
            } else {
                val adapter = MovieVerticalAdapter(movies)
                val layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                FlexboxLayoutManager(this)
                val recyclerView = binding.rvFavorite
                recyclerView.apply {
                    this.adapter = adapter
                    this.setHasFixedSize(true)
                    this.layoutManager = layoutManager
                }
                adapter.onItemClick = { selectedData ->
                    val intent =
                        Intent(this@FavoriteActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.extraData, selectedData)
                    startActivity(intent)
                }
            }
        }
    }
}

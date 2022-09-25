package com.robert.movieapp.view.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.robert.movieapp.R
import com.robert.movieapp.databinding.ActivitySearchBinding
import com.robert.movieapp.ui.MovieVerticalAdapter
import com.robert.movieapp.view.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.search)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchItem.expandActionView()
        searchView.queryHint = getString(R.string.movieTitle)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                findMovieByQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                onBackPressed()
                return false
            }
        })
        return true
    }

    internal fun findMovieByQuery(query: String) {
        searchViewModel.findMovieByQuery(query).observe(this) { result ->
            result.onSuccess { movies ->

                val adapter = MovieVerticalAdapter(movies)
                adapter.onItemClick = { selectedData ->
                    val intent =
                        Intent(this@SearchActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.extraData, selectedData)
                    startActivity(intent)
                }

                val layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                FlexboxLayoutManager(this)
                val recyclerView = binding.rvSearchMovies
                recyclerView.apply {
                    this.adapter = adapter
                    this.setHasFixedSize(true)
                    this.layoutManager = layoutManager
                }
            }
            result.onFailure {
                Log.e("search activity", "error")
            }
        }
    }
}

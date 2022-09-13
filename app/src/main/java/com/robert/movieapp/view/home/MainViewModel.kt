package com.robert.movieapp.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.robert.movieapp.domain.usecase.MovieUseCase

class MainViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun findMoviesNowPlaying(region: String) =
        movieUseCase.findMoviesNowPlaying(region).asLiveData()

    fun findTrendingMovies(region: String) =
        movieUseCase.findTrendingMovies(region).asLiveData()

    fun findPopularMovies(region: String) =
        movieUseCase.findPopularMovies(region).asLiveData()

    fun findUpComingMovies(region: String) =
        movieUseCase.findUpComingMovies(region).asLiveData()
}

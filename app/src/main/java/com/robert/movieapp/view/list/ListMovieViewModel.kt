package com.robert.movieapp.view.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.robert.movieapp.domain.usecase.MovieUseCase

class ListMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun findTrendingMovies(region: String) =
        movieUseCase.findTrendingMovies(region).asLiveData()

    fun findPopularMovies(region: String) =
        movieUseCase.findPopularMovies(region).asLiveData()

    fun findUpComingMovies(region: String) =
        movieUseCase.findUpComingMovies(region).asLiveData()
}

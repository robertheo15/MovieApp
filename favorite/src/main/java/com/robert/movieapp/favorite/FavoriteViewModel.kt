package com.robert.movieapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.robert.movieapp.domain.usecase.MovieUseCase

class FavoriteViewModel(val movieUseCase: MovieUseCase) : ViewModel() {

    fun findFavoriteMovies() =
        movieUseCase.findFavoriteMovies().asLiveData()
}

package com.robert.movieapp.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.robert.movieapp.domain.usecase.MovieUseCase

class SearchViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun findMovieByQuery(query: String) =
        movieUseCase.findMovieByQuery(query).asLiveData()
}

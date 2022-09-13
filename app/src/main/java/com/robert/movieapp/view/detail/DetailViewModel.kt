package com.robert.movieapp.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.robert.movieapp.R
import com.robert.movieapp.domain.model.Movie
import com.robert.movieapp.domain.usecase.MovieUseCase
import com.robert.movieapp.utils.Event
import kotlinx.coroutines.launch

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _snackBarText = MutableLiveData<Event<Int>>()
    val snackBarText: LiveData<Event<Int>> = _snackBarText

    fun createMovieAsFavorite(movie: Movie, state: Boolean) =
        viewModelScope.launch {
            movieUseCase.createMovieAsFavorite(movie, state)
            if (state) {
                _snackBarText.value = Event(R.string.movieMarkedFavorite)
            } else {
                _snackBarText.value = Event(R.string.movieMarkedNotFavorite)
            }
        }

    fun findRecommendationMovies(id: Int) =
        movieUseCase.findRecommendationMovies(id).asLiveData()

    fun findMovieCasts(id: Int) =
        movieUseCase.findMovieCasts(id).asLiveData()
}

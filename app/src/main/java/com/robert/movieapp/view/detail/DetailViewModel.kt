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

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _hasSetMovie = MutableLiveData(false)
    val hasSetMovie: LiveData<Boolean> = _hasSetMovie

    fun setMovie(movie: Movie) {
        _movie.value = movie
        _isFavorite.value = movie.isFavorite
        _hasSetMovie.value = true
    }

    fun createMovieAsFavorite(movie: Movie, state: Boolean) =
        viewModelScope.launch {
            movieUseCase.createMovieAsFavorite(movie, state)
            if (state) {
                _movie.value = movie
                _isFavorite.value = state
                _snackBarText.value = Event(R.string.movieMarkedFavorite)
            } else {
                _movie.value = movie
                _isFavorite.value = state
                _snackBarText.value = Event(R.string.movieMarkedNotFavorite)
            }
        }

    fun findRecommendationMovies(id: Int) =
        movieUseCase.findRecommendationMovies(id).asLiveData()

    fun findMovieCasts(id: Int) =
        movieUseCase.findMovieCasts(id).asLiveData()
}

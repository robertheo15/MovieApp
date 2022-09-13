package com.robert.movieapp.data.source.local

import com.robert.movieapp.data.source.local.entity.MovieEntity
import com.robert.movieapp.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovie(category: Int): Flow<List<MovieEntity>> = movieDao.findMovies(category)

    fun findFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.findFavoriteMovies()

    suspend fun insertMovie(movieList: List<MovieEntity>) = movieDao.insertMovie(movieList)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        if (newState) {
            movie.isFavorite = newState
            movieDao.insertMovieFavorite(movie)
            movieDao.updateFavoriteMovie(movie)
        } else {
            movieDao.deleteFavoriteMovie(movie)
        }
    }
}

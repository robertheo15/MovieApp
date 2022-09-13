package com.robert.movieapp.domain.repository

import com.robert.movieapp.data.Resource
import com.robert.movieapp.domain.model.Cast
import com.robert.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun findMoviesNowPlaying(region: String): Flow<Resource<List<Movie>>>

    fun findTrendingMovies(region: String): Flow<Resource<List<Movie>>>

    fun findUpComingMovies(region: String): Flow<Resource<List<Movie>>>

    fun findPopularMovies(region: String): Flow<Resource<List<Movie>>>

    fun findRecommendationMovies(id: Int): Flow<Result<List<Movie>>>

    fun findMovieCasts(id: Int): Flow<Result<List<Cast>>>

    fun findMovieByQuery(query: String): Flow<Result<List<Movie>>>

    fun findFavoriteMovies(): Flow<List<Movie>>

    suspend fun setMovieAsFavorite(movie: Movie, state: Boolean)
}

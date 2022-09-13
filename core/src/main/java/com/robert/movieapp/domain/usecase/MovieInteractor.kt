package com.robert.movieapp.domain.usecase

import com.robert.movieapp.data.Resource
import com.robert.movieapp.domain.model.Cast
import com.robert.movieapp.domain.model.Movie
import com.robert.movieapp.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun findMoviesNowPlaying(region: String) =
        movieRepository.findMoviesNowPlaying(region)

    override fun findTrendingMovies(region: String): Flow<Resource<List<Movie>>> =
        movieRepository.findTrendingMovies(region)

    override fun findUpComingMovies(region: String): Flow<Resource<List<Movie>>> =
        movieRepository.findUpComingMovies(region)

    override fun findPopularMovies(region: String): Flow<Resource<List<Movie>>> =
        movieRepository.findPopularMovies(region)

    override fun findRecommendationMovies(id: Int): Flow<Result<List<Movie>>> =
        movieRepository.findRecommendationMovies(id)

    override fun findMovieCasts(id: Int): Flow<Result<List<Cast>>> =
        movieRepository.findMovieCasts(id)

    override fun findMovieByQuery(query: String): Flow<Result<List<Movie>>> =
        movieRepository.findMovieByQuery(query)

    override fun findFavoriteMovies(): Flow<List<Movie>> =
        movieRepository.findFavoriteMovies()

    override suspend fun createMovieAsFavorite(movie: Movie, state: Boolean) =
        movieRepository.setMovieAsFavorite(movie, state)
}

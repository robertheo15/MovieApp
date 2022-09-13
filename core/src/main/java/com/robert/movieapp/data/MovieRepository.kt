package com.robert.movieapp.data

import com.robert.movieapp.data.source.local.LocalDataSource
import com.robert.movieapp.data.source.remote.RemoteDataSource
import com.robert.movieapp.data.source.remote.network.ApiResponse
import com.robert.movieapp.data.source.remote.network.ApiService
import com.robert.movieapp.data.source.remote.response.MovieResponse
import com.robert.movieapp.domain.model.Cast
import com.robert.movieapp.domain.model.Movie
import com.robert.movieapp.domain.repository.IMovieRepository
import com.robert.movieapp.utils.AppExecutors
import com.robert.movieapp.utils.DataMapper
import com.robert.movieapp.utils.DataMapper.castResponseToDomain
import com.robert.movieapp.utils.DataMapper.movieResponseToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val apiService: ApiService
) : IMovieRepository {
    override fun findMoviesNowPlaying(region: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie(0).map {
                    DataMapper.movieEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.findMoviesNowPlaying(region)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.movieResponseToEntities(data, 0)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun findTrendingMovies(region: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie(1).map {
                    DataMapper.movieEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.findTrendingMovies(region)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.movieResponseToEntities(data, 1)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun findPopularMovies(region: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie(2).map {
                    DataMapper.movieEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.findPopularMovies(region)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.movieResponseToEntities(data, 2)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun findUpComingMovies(region: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie(3).map {
                    DataMapper.movieEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.findUpComingMovies(region)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.movieResponseToEntities(data, 3)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun findRecommendationMovies(id: Int): Flow<Result<List<Movie>>> =
        flow {
            val response = apiService.findRecommendationMovies(id).results
            val movies = mutableListOf<Movie>()
            response?.forEach { movieResponse ->
                movies.add(movieResponseToDomain(movieResponse))
            }

            emit(Result.success(movies))
        }.catch { e ->
            emit(Result.failure(e))
        }

    override fun findMovieCasts(id: Int): Flow<Result<List<Cast>>> =
        flow {
            val response = apiService.findMovieCasts(id).casts
            val casts = mutableListOf<Cast>()
            response?.forEach { castResponse ->
                casts.add(castResponseToDomain(castResponse))
            }

            emit(Result.success(casts))
        }.catch { e ->
            emit(Result.failure(e))
        }

    override fun findMovieByQuery(query: String): Flow<Result<List<Movie>>> =
        flow {
            val response = apiService.findMovieByQuery(query).results
            val movies = mutableListOf<Movie>()
            response?.forEach { movieResponse ->
                movies.add(movieResponseToDomain(movieResponse))
            }

            emit(Result.success(movies))
        }.catch { e ->
            emit(Result.failure(e))
        }

    override fun findFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.findFavoriteMovies().map {
            DataMapper.movieEntityToDomain(it)
        }
    }

    override suspend fun setMovieAsFavorite(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.movieDomainToEntity(movie, movie.category)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }
}

package com.robert.movieapp.data.source.remote

import android.util.Log
import com.robert.movieapp.data.source.remote.network.ApiResponse
import com.robert.movieapp.data.source.remote.network.ApiService
import com.robert.movieapp.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun findMoviesNowPlaying(region: String): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.findMoviesNowPlaying(region)
                val data = response.results
                if (data != null) {
                    if (data.isNotEmpty()) {
                        emit(ApiResponse.Success(data))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun findTrendingMovies(region: String): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.findTrendingMovies(region)
                val data = response.results
                if (data != null) {
                    if (data.isNotEmpty()) {
                        emit(ApiResponse.Success(data))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun findPopularMovies(region: String): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.findPopularMovies(region)
                val data = response.results
                if (data != null) {
                    if (data.isNotEmpty()) {
                        emit(ApiResponse.Success(data))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun findUpComingMovies(region: String): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.findUpComingMovies(region)
                val data = response.results
                if (data != null) {
                    if (data.isNotEmpty()) {
                        emit(ApiResponse.Success(data))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

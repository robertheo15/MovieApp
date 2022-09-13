package com.robert.movieapp.data.source.remote.network

import com.robert.movieapp.core.BuildConfig.API_KEY
import com.robert.movieapp.data.source.remote.response.ListCastResponse
import com.robert.movieapp.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun findMoviesNowPlaying(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("trending/movie/week")
    suspend fun findTrendingMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("movie/popular")
    suspend fun findPopularMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("movie/upcoming")
    suspend fun findUpComingMovies(
        @Query("region") region: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun findRecommendationMovies(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun findMovieCasts(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ListCastResponse

    @GET("search/movie")
    suspend fun findMovieByQuery(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse
}

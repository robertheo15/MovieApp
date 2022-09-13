package com.robert.movieapp.utils

import com.robert.movieapp.data.source.local.entity.MovieEntity
import com.robert.movieapp.data.source.remote.response.CastResponse
import com.robert.movieapp.data.source.remote.response.MovieResponse
import com.robert.movieapp.domain.model.Cast
import com.robert.movieapp.domain.model.Movie

object DataMapper {
    fun movieResponseToDomain(input: MovieResponse): Movie {
        return Movie(
            id = input.id,
            title = input.title,
            overview = input.overview,
            posterPath = input.posterPath,
            voteAverage = input.voteAverage,
            category = 0,
            isFavorite = false
        )
    }

    fun movieResponseToEntities(input: List<MovieResponse>, category: Int): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                isFavorite = false,
                category = category
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun movieEntityToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                category = it.category,
                isFavorite = it.isFavorite
            )
        }

    fun movieDomainToEntity(input: Movie, category: Int) =
        MovieEntity(
            id = input.id,
            title = input.title,
            overview = input.overview,
            posterPath = input.posterPath.toString(),
            voteAverage = input.voteAverage,
            isFavorite = false,
            category = category
        )

    fun castResponseToDomain(input: CastResponse): Cast =
        Cast(
            id = input.id,
            name = input.name,
            character = input.character,
            profilePath = input.profilePath
        )
}

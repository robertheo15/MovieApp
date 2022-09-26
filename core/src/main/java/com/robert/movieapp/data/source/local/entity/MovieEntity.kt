package com.robert.movieapp.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "posterPath")
    val posterPath: String?,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean,

    @ColumnInfo(name = "category")
    var category: Int
)

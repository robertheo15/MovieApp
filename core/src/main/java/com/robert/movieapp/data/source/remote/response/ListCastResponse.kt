package com.robert.movieapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListCastResponse(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("cast")
    val casts: List<CastResponse>? = null
)

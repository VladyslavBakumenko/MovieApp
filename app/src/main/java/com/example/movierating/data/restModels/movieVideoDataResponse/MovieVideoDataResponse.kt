package com.example.movierating.data.restModels.movieVideoDataResponse

import com.google.gson.annotations.SerializedName

data class MovieVideoDataResponse(
    @SerializedName("id")
    var id: Int,

    @SerializedName("results")
    var results: List<MovieVideoData> = arrayListOf()
)
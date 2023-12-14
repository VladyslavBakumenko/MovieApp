package com.example.movierating.data.restModels.movieCreditsResponse

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("cast") var cast: List<Actor>,
    @SerializedName("crew") var crew: List<Crew>
)
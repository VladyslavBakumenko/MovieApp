package com.example.movierating.data

import com.google.gson.annotations.SerializedName

data class RequestTokenBody(
    @SerializedName("request_token")
    val requestToken: String?
)
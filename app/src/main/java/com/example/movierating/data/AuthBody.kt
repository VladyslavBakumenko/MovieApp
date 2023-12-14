package com.example.movierating.data

import com.google.gson.annotations.SerializedName

data class AuthBody(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("request_token")
    val token: String?
)
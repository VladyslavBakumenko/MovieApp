package com.example.movierating.data

import com.google.gson.annotations.SerializedName

data class SessionIdResponse(
    @SerializedName("session_id")
    val sessionId: String
)
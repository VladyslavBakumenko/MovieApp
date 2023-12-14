package com.example.movierating.domain.di.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
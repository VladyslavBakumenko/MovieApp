package com.example.movierating.domain.network.apiRepository

import com.example.movierating.data.restModels.MovieListResponse
import com.example.movierating.data.restModels.movieCreditsResponse.MovieCreditsResponse
import com.example.movierating.data.restModels.movieVideoDataResponse.MovieVideoDataResponse
import com.example.movierating.data.restModels.personDetailsResponse.PersonDetailsResponse
import com.example.movierating.data.restModels.profileImagesResponse.ProfileImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("/3/search/movie")
    suspend fun getSearchedMovie(
        @Query("query") searedMovieText: String,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("/3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getMovieVideoData(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = LANGUAGE
    ): Response<MovieVideoDataResponse>

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = LANGUAGE
    ): Response<MovieCreditsResponse>

    @GET("/3/person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") movieId: Int,
        @Query("language") language: String = LANGUAGE
    ): Response<PersonDetailsResponse>

    @GET("/3/person/{person_id}/images")
    suspend fun getPersonImages(
        @Path("person_id") movieId: Int,
        @Query("language") language: String = LANGUAGE
    ): Response<ProfileImagesResponse>

    companion object {
        private const val LANGUAGE = "en-US"
    }
}
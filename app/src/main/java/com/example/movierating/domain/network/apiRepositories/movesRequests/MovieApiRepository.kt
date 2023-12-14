package com.example.movierating.domain.network.apiRepositories.movesRequests

import androidx.paging.PagingData
import com.example.movierating.data.restModels.Movie
import com.example.movierating.data.restModels.movieCreditsResponse.MovieCreditsResponse
import com.example.movierating.data.restModels.movieVideoDataResponse.MovieVideoDataResponse
import com.example.movierating.data.restModels.personDetailsResponse.PersonDetailsResponse
import com.example.movierating.data.restModels.profileImagesResponse.ProfileImagesResponse
import com.example.movierating.domain.network.exceptionHendling.SallyResponseResource
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieApiRepository {
    fun getMoviesPagingDataFlow(
        moviePagingSourceRequestType: MoviePagingSourceRequestType,
        searchedMovieText: String? = null,
        movieId: Int? = null
    ): Flow<PagingData<Movie>>

    fun getMovieVideoData(movieId: Int): Flow<SallyResponseResource<Response<MovieVideoDataResponse>>>

    fun getMovieCredits(movieId: Int): Flow<SallyResponseResource<Response<MovieCreditsResponse>>>

    fun getPersonDetails(personId: Int): Flow<SallyResponseResource<Response<PersonDetailsResponse>>>

    fun getPersonImages(personId: Int): Flow<SallyResponseResource<Response<ProfileImagesResponse>>>
}
package com.example.movierating.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import asSallyResponseResourceFlow
import com.example.movierating.data.restModels.Movie
import com.example.movierating.data.restModels.movieCreditsResponse.MovieCreditsResponse
import com.example.movierating.data.restModels.movieVideoDataResponse.MovieVideoDataResponse
import com.example.movierating.data.restModels.personDetailsResponse.PersonDetailsResponse
import com.example.movierating.data.restModels.profileImagesResponse.ProfileImagesResponse
import com.example.movierating.domain.network.apiRepository.MovieApiRepository
import com.example.movierating.domain.network.apiRepository.MovieApiService
import com.example.movierating.domain.network.exceptionHandling.SallyResponseResource
import com.example.movierating.domain.network.paging.MoviePagingSource
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MovieApiRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val moviePagingSourceFactory: MoviePagingSource.Factory
) : MovieApiRepository {

    override fun getMoviesPagingDataFlow(
        moviePagingSourceRequestType: MoviePagingSourceRequestType,
        searchedMovieText: String?,
        movieId: Int?
    ): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            moviePagingSourceFactory.create(
                moviePagingSourceRequestType = moviePagingSourceRequestType,
                searchedMovieText = searchedMovieText,
                movieId = movieId
            )
        }
    ).flow

    override fun getMovieVideoData(movieId: Int): Flow<SallyResponseResource<Response<MovieVideoDataResponse>>> {
        return flow {
            emit(movieApiService.getMovieVideoData(movieId = movieId))
        }.asSallyResponseResourceFlow()
    }

    override fun getMovieCredits(movieId: Int): Flow<SallyResponseResource<Response<MovieCreditsResponse>>> {
        return flow {
            emit(movieApiService.getMovieCredits(movieId = movieId))
        }.asSallyResponseResourceFlow()
    }

    override fun getPersonDetails(personId: Int): Flow<SallyResponseResource<Response<PersonDetailsResponse>>> {
        return flow {
            emit(movieApiService.getPersonDetails(personId))
        }.asSallyResponseResourceFlow()
    }

    override fun getPersonImages(personId: Int): Flow<SallyResponseResource<Response<ProfileImagesResponse>>> {
        return flow {
            emit(movieApiService.getPersonImages(personId))
        }.asSallyResponseResourceFlow()
    }
}
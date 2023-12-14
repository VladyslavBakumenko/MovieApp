package com.example.movierating.domain.network.usecases

import com.example.movierating.data.restModels.movieVideoDataResponse.MovieVideoDataResponse
import com.example.movierating.domain.network.apiRepositories.movesRequests.MovieApiRepository
import com.example.movierating.domain.network.exceptionHendling.SallyResponseResource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetMovieVideoDataUseCase @Inject constructor(private val movieApiRepository: MovieApiRepository) {
    operator fun invoke(movieId: Int): Flow<SallyResponseResource<Response<MovieVideoDataResponse>>> {
        return movieApiRepository.getMovieVideoData(movieId)
    }
}
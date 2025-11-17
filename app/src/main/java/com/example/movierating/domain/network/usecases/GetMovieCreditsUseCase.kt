package com.example.movierating.domain.network.usecases

import com.example.movierating.data.restModels.movieCreditsResponse.MovieCreditsResponse
import com.example.movierating.domain.network.apiRepository.MovieApiRepository
import com.example.movierating.domain.network.exceptionHandling.SallyResponseResource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(private val movieApiRepository: MovieApiRepository) {

    operator fun invoke(movieId: Int): Flow<SallyResponseResource<Response<MovieCreditsResponse>>> {
        return movieApiRepository.getMovieCredits(movieId)
    }
}
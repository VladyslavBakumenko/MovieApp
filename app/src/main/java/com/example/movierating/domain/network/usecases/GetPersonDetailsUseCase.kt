package com.example.movierating.domain.network.usecases

import com.example.movierating.data.restModels.personDetailsResponse.PersonDetailsResponse
import com.example.movierating.domain.network.apiRepositories.movesRequests.MovieApiRepository
import com.example.movierating.domain.network.exceptionHendling.SallyResponseResource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(private val movieApiRepository: MovieApiRepository) {
    operator fun invoke(personId: Int): Flow<SallyResponseResource<Response<PersonDetailsResponse>>> {
        return movieApiRepository.getPersonDetails(personId)
    }
}
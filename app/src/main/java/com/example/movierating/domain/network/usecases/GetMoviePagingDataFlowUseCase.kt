package com.example.movierating.domain.network.usecases

import androidx.paging.PagingData
import com.example.movierating.data.restModels.Movie
import com.example.movierating.domain.network.apiRepository.MovieApiRepository
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviePagingDataFlowUseCase @Inject constructor(private val movieApiRepository: MovieApiRepository) {
    fun getMoviePagingDataFlow(
        moviePagingSourceRequestType: MoviePagingSourceRequestType,
        searchedMovieText: String? = null,
        movieId: Int? = null
    ): Flow<PagingData<Movie>> {
        return movieApiRepository.getMoviesPagingDataFlow(
            moviePagingSourceRequestType,
            searchedMovieText,
            movieId
        )
    }
}
package com.example.movierating.domain.network.usecases

import androidx.paging.PagingData
import com.example.movierating.data.restModels.Movie
import com.example.movierating.domain.network.apiRepositories.movesRequests.MovieApiRepositoryImpl
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviePagingDataFlowUseCase @Inject constructor(private val movieApiRepositoryImpl: MovieApiRepositoryImpl) {
    fun getMoviePagingDataFlow(
        moviePagingSourceRequestType: MoviePagingSourceRequestType,
        searchedMovieText: String? = null,
        movieId: Int? = null
    ): Flow<PagingData<Movie>> {
        return movieApiRepositoryImpl.getMoviesPagingDataFlow(
            moviePagingSourceRequestType,
            searchedMovieText,
            movieId
        )
    }
}
package com.example.movierating.presentation.allMoviesListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movierating.data.restModels.Movie
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import com.example.movierating.domain.network.usecases.GetMoviePagingDataFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AllMoviesListScreenViewModel @Inject constructor(
    private val getMoviePagingDataFlowUseCase: GetMoviePagingDataFlowUseCase
) : ViewModel() {

    fun getMovieListFlow(moviePagingSourceRequestType: MoviePagingSourceRequestType): Flow<PagingData<Movie>> {
        return getMoviePagingDataFlowUseCase.getMoviePagingDataFlow(
            moviePagingSourceRequestType
        ).cachedIn(viewModelScope)
    }
}
package com.example.movierating.presentation.allMoviesListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movierating.data.restModels.Movie
import com.example.movierating.domain.network.apiRepositories.movesRequests.MovieApiRepositoryImpl
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import com.example.movierating.domain.network.usecases.GetMoviePagingDataFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AllMoviesListScreenViewModel @Inject constructor(
    private val getMoviePagingDataFlowUseCase: GetMoviePagingDataFlowUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AllMoviesListScreenState.getInstance(getMovieListFlow()))
    val state = _state.asStateFlow()

    private fun getMovieListFlow(): Flow<PagingData<Movie>> {
        return getMoviePagingDataFlowUseCase.getMoviePagingDataFlow(
            MoviePagingSourceRequestType.GET_BY_POPULAR
        ).cachedIn(viewModelScope)
    }
}
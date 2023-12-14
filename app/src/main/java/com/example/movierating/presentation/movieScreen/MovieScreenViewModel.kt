package com.example.movierating.presentation.movieScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movierating.data.restModels.Movie
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import com.example.movierating.domain.network.usecases.GetMoviePagingDataFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieScreenViewModel @Inject constructor(
    private val getMoviePagingDataFlowUseCase: GetMoviePagingDataFlowUseCase

) : ViewModel() {

    private val _state = MutableStateFlow(createState())
    val state = _state.asStateFlow()

    private fun createState(): MovieScreenState {
        return MovieScreenState.getInstance(
            getMovieListFlow(MoviePagingSourceRequestType.GET_BY_POPULAR),
            getMovieListFlow(MoviePagingSourceRequestType.GET_BY_TOP_RATED),
            getMovieListFlow(MoviePagingSourceRequestType.GET_UPCOMING)
        )
    }

    private fun getMovieListFlow(moviePagingSourceRequestType: MoviePagingSourceRequestType): Flow<PagingData<Movie>> {
       return getMoviePagingDataFlowUseCase.getMoviePagingDataFlow(moviePagingSourceRequestType).cachedIn(viewModelScope)
    }
}
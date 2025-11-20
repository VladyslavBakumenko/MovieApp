package com.example.movierating.presentation.allMoviesListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movierating.data.restModels.Movie
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import com.example.movierating.domain.network.usecases.GetMoviePagingDataFlowUseCase
import com.example.movierating.presentation.searchMovieScreen.SearchMovieScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AllMoviesListScreenViewModel @Inject constructor(
    private val getMoviePagingDataFlowUseCase: GetMoviePagingDataFlowUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        AllMoviesListScreenState(movieFlow = kotlinx.coroutines.flow.emptyFlow())
    )
    val state = _state.asStateFlow()

    fun onIntent(intent: AllMoviesListIntent) {
        when (intent) {
            is AllMoviesListIntent.OnFirstPageLoadingState -> {
                changeIsLoadingMovieState(intent.isLoading)
            }
            AllMoviesListIntent.LoadMovies -> {
                loadMovies()
            }
        }
    }

    private fun loadMovies() {
        val flow = getMoviePagingDataFlowUseCase
            .getMoviePagingDataFlow(MoviePagingSourceRequestType.GET_BY_POPULAR)
            .cachedIn(viewModelScope)

        _state.update { it.copy(movieFlow = flow) }
    }

    private fun changeIsLoadingMovieState(isLoadingMovies: Boolean) {
        _state.update { it.copy(isLoadingMovies = isLoadingMovies) }
    }
}
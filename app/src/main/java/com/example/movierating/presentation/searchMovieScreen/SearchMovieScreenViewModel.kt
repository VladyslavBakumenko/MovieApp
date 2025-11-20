package com.example.movierating.presentation.searchMovieScreen

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
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchMovieScreenViewModel @Inject constructor(
    private val getMoviePagingDataFlowUseCase: GetMoviePagingDataFlowUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchMovieScreenState.getInstance(getMovieList()))
    val state = _state.asStateFlow()

    fun onIntent(intent: SearchMovieIntent) {
        when (intent) {
            is SearchMovieIntent.OnSearchTextChanged -> changeSearchedMovieTextState(intent.text)
            is SearchMovieIntent.OnFirstPageLoadingState -> changeIsLoadingMovieState(intent.isLoading)
        }
    }

    private fun changeSearchedMovieTextState(text: String) {
        _state.update {
            it.copy(searchedMovieTextState = text, movieFlow = getMovieList(text))
        }
    }

    private fun changeIsLoadingMovieState(isLoadingMovies: Boolean) {
        _state.update {
            it.copy(isLoadingMovies = isLoadingMovies)
        }
    }

    private fun getMovieList(searchedMovieText: String? = null): Flow<PagingData<Movie>> {
        return getMoviePagingDataFlowUseCase.getMoviePagingDataFlow(
            MoviePagingSourceRequestType.GET_SEARCHED,
            searchedMovieText
        ).cachedIn(viewModelScope)
    }
}
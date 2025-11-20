package com.example.movierating.presentation.movieDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierating.domain.network.exceptionHandling.SallyResponseResource
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import com.example.movierating.domain.network.usecases.GetMovieCreditsUseCase
import com.example.movierating.domain.network.usecases.GetMoviePagingDataFlowUseCase
import com.example.movierating.domain.network.usecases.GetMovieVideoDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    private val getMoviePagingDataFlowUseCase: GetMoviePagingDataFlowUseCase,
    private val getMovieVideoDataUseCase: GetMovieVideoDataUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailsScreenState())
    val state = _state.asStateFlow()

    fun onIntent(movieDetailsIntent: MovieDetailsIntent) {
        when (movieDetailsIntent) {
            is MovieDetailsIntent.LoadMovieCredits -> getMovieCredits(movieDetailsIntent.movieId)
            is MovieDetailsIntent.LoadMovieVideos -> getMovieVideoData(movieDetailsIntent.movieId)
            is MovieDetailsIntent.LoadSimilarMovies -> getMoviePagingDataFlow(movieDetailsIntent.movieId)
        }
    }

    private fun getMoviePagingDataFlow(movieId: Int) {
        _state.update {
            it.copy(
                similarMovieFlow =
                    getMoviePagingDataFlowUseCase.getMoviePagingDataFlow(
                        MoviePagingSourceRequestType.GET_SIMILAR,
                        movieId = movieId
                    )
            )
        }
    }

    private fun getMovieVideoData(movieId: Int) {
        viewModelScope.launch {
            getMovieVideoDataUseCase.invoke(movieId).collectLatest {
                when (it) {
                    is SallyResponseResource.Error -> {

                    }

                    is SallyResponseResource.Loading -> {

                    }

                    is SallyResponseResource.Success -> {
                        _state.update { prevState ->
                            prevState.copy(movieVideoData = it.data.body()?.results.orEmpty())
                        }
                    }
                }
            }
        }
    }

    private fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            getMovieCreditsUseCase.invoke(movieId).collectLatest {
                when (it) {
                    is SallyResponseResource.Error -> {

                    }

                    is SallyResponseResource.Loading -> {

                    }

                    is SallyResponseResource.Success -> {
                        _state.update { prevState ->
                            prevState.copy(cast = it.data.body()?.cast.orEmpty())
                        }
                    }
                }
            }
        }
    }
}



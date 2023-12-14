package com.example.movierating.presentation.movieScreen

import androidx.paging.PagingData
import com.example.movierating.data.restModels.Movie
import kotlinx.coroutines.flow.Flow

data class MovieScreenState(
    val popularMovieFlow: Flow<PagingData<Movie>>,
    val topRatedMovieFlow: Flow<PagingData<Movie>>,
    val upcomingMovieFlow: Flow<PagingData<Movie>>
) {
    companion object {
        fun getInstance(
            popularMovieFlow: Flow<PagingData<Movie>>,
            topRatedMovieFlow: Flow<PagingData<Movie>>,
            upcomingMovieFlow: Flow<PagingData<Movie>>
        ): MovieScreenState {
           return MovieScreenState(popularMovieFlow, topRatedMovieFlow, upcomingMovieFlow)
        }
    }
}
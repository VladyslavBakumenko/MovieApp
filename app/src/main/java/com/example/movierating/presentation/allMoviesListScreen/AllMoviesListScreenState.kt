package com.example.movierating.presentation.allMoviesListScreen

import androidx.paging.PagingData
import com.example.movierating.data.restModels.Movie
import kotlinx.coroutines.flow.Flow

data class AllMoviesListScreenState(val popularMovieFlow: Flow<PagingData<Movie>>) {

    companion object {
        fun getInstance(popularMovieFlow: Flow<PagingData<Movie>>): AllMoviesListScreenState {
            return AllMoviesListScreenState(popularMovieFlow)
        }
    }
}
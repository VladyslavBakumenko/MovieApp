package com.example.movierating.presentation.allMoviesListScreen

import androidx.paging.PagingData
import com.example.movierating.data.restModels.Movie
import com.example.movierating.presentation.searchMovieScreen.SearchMovieScreenState
import kotlinx.coroutines.flow.Flow

data class AllMoviesListScreenState(
    val isLoadingMovies: Boolean = false,
    val movieFlow: Flow<PagingData<Movie>>
) {

    companion object {
        fun getInstance(movieFlow: Flow<PagingData<Movie>>): AllMoviesListScreenState {
            return AllMoviesListScreenState(movieFlow = movieFlow)
        }
    }
}
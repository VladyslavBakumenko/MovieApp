package com.example.movierating.presentation.searchMovieScreen

import androidx.paging.PagingData
import com.example.movierating.data.restModels.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class SearchMovieScreenState @Inject constructor(
    val searchedMovieTextState: String = "",
    val isLoadingMovies: Boolean = false,
    val movieFlow: Flow<PagingData<Movie>>
) {

    companion object {
        fun getInstance(movieFlow: Flow<PagingData<Movie>>): SearchMovieScreenState {
            return SearchMovieScreenState(movieFlow = movieFlow)
        }
    }
}

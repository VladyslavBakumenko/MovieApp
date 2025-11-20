package com.example.movierating.presentation.searchMovieScreen

sealed class SearchMovieIntent {
    data class OnSearchTextChanged(val text: String) : SearchMovieIntent()
    data class OnFirstPageLoadingState(val isLoading: Boolean) : SearchMovieIntent()
}
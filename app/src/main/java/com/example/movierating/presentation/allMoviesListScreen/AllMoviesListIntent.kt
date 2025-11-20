package com.example.movierating.presentation.allMoviesListScreen


sealed class AllMoviesListIntent {
    data class OnFirstPageLoadingState(val isLoading: Boolean) : AllMoviesListIntent()
    data object LoadMovies : AllMoviesListIntent()
}
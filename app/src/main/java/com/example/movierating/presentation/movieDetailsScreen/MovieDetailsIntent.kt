package com.example.movierating.presentation.movieDetailsScreen

sealed class MovieDetailsIntent {
    data class LoadSimilarMovies(val movieId: Int) : MovieDetailsIntent()
    data class LoadMovieVideos(val movieId: Int) : MovieDetailsIntent()
    data class LoadMovieCredits(val movieId: Int) : MovieDetailsIntent()
}
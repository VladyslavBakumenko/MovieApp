package com.example.movierating.presentation.movieDetailsScreen

import androidx.paging.PagingData
import com.example.movierating.data.restModels.Movie
import com.example.movierating.data.restModels.movieCreditsResponse.Actor
import com.example.movierating.data.restModels.movieVideoDataResponse.MovieVideoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class MovieDetailsScreenState(
    val similarMovieFlow: Flow<PagingData<Movie>> = flow {  },
    val cast: List<Actor> = emptyList(),
    val movieVideoData: List<MovieVideoData> = emptyList()
)
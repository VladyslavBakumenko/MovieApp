package com.example.movierating.presentation.allMoviesListScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.movierating.data.restModels.Movie
import com.example.movierating.presentation.utils.MovieActorItem
import kotlinx.coroutines.flow.Flow

@Composable
fun AllMoviesListScreen(
    pagingMoviesFlow: Flow<PagingData<Movie>>,
    startMovieDetailsListener: (movie: Movie) -> Unit,
    firstPageLoadingListener: () -> Unit,
    firstPageLoadingSuccessListener: () -> Unit
) {

    val moviesList = pagingMoviesFlow.collectAsLazyPagingItems()

    when (moviesList.loadState.refresh) {
        is LoadState.Loading -> {
            firstPageLoadingListener()
        }

        is LoadState.NotLoading -> {
            firstPageLoadingSuccessListener()
        }

        else -> {}
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(
                    count = moviesList.itemCount,
                    key = moviesList.itemKey(),
                    contentType = moviesList.itemContentType()
                ) { index ->
                    moviesList[index]?.let {
                        MovieActorItem(
                            movieOrActor = it,
                            horizontalPaddingVales = PaddingValues(),
                            onClick = { clickedMovie ->
                                startMovieDetailsListener(clickedMovie)
                            })
                    }
                }
            }
        }
    }
}
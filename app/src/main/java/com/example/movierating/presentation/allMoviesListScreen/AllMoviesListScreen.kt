package com.example.movierating.presentation.allMoviesListScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.movierating.data.restModels.Movie
import com.example.movierating.presentation.searchMovieScreen.SearchMovieScreenViewModel
import com.example.movierating.presentation.utils.MovieActorItem
import com.example.movierating.presentation.utils.MoviePosterSkeleton
import kotlinx.coroutines.flow.Flow

@Composable
fun AllMoviesListScreen(
    startMovieDetailsListener: (movie: Movie) -> Unit,
) {
    val viewModel = hiltViewModel<AllMoviesListScreenViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(AllMoviesListIntent.LoadMovies)
    }

    val movieList = state.movieFlow.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            movieList.loadState.refresh is LoadState.Loading -> MoviesGridSkeleton()
            else -> LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(
                    count = movieList.itemCount,
                    key = movieList.itemKey(),
                    contentType = movieList.itemContentType()
                ) { index ->
                    movieList[index]?.let {
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

@Composable
private fun MoviesGridSkeleton(
    columns: Int = 2,
    placeholderCount: Int = 6
) {
    LazyVerticalGrid(columns = GridCells.Fixed(columns)) {
        items(placeholderCount) {
            MoviePosterSkeleton(
                paddingValues = PaddingValues(16.dp),
                showTitlePlaceholder = false
            )
        }
    }
}
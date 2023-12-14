package com.example.movierating.presentation.movieScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.example.movierating.R
import com.example.movierating.data.restModels.Movie
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import com.example.movierating.presentation.utils.PagingHorizontalMovieList
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    paddingValues: PaddingValues,
    startMovieDetailsListener: (movie: Movie) -> Unit,
    allFilmsClickListener: (moviePagingSourceRequestType: MoviePagingSourceRequestType) -> Unit,
    searchListener: () -> Unit
) {

    val viewModel = hiltViewModel<MovieScreenViewModel>()
    val screenState by viewModel.state.collectAsState()

    Scaffold(
        Modifier.fillMaxSize(), content = {
            LazyColumn(Modifier.padding(it)) {
                item {
                    Box(Modifier.wrapContentSize()) {
                        MoviesCategoriesList(
                            screenState.popularMovieFlow,
                            MoviePagingSourceRequestType.GET_BY_POPULAR,
                            stringResource(R.string.popular_text),
                            startMovieDetailsListener,
                            allFilmsClickListener
                        )
                    }
                }
                item {
                    Box(Modifier.wrapContentSize()) {
                        MoviesCategoriesList(
                            screenState.topRatedMovieFlow,
                            MoviePagingSourceRequestType.GET_BY_TOP_RATED,
                            stringResource(R.string.top_rated_text),
                            startMovieDetailsListener,
                            allFilmsClickListener
                        )
                    }

                    Box(Modifier.wrapContentSize()) {
                        MoviesCategoriesList(
                            screenState.upcomingMovieFlow,
                            MoviePagingSourceRequestType.GET_BY_TOP_RATED,
                            stringResource(R.string.top_upcoming_text),
                            startMovieDetailsListener,
                            allFilmsClickListener
                        )
                    }
                }
                item { Spacer(modifier = Modifier.size(16.dp)) }
            }
        }, topBar = {
            TopAppBar(title = {
                Text(
                    stringResource(id = R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
                actions = {
                    IconButton(onClick = { searchListener() }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null
                        )
                    }
                })
        })
}

@Composable
private fun MoviesCategoriesList(
    moviePagingState: Flow<PagingData<Movie>>,
    moviePagingSourceRequestType: MoviePagingSourceRequestType,
    moviesCategoriesListHeaderText: String,
    startMovieDetailsListener: (movie: Movie) -> Unit,
    allFilmsClickListener: (moviePagingSourceRequestType: MoviePagingSourceRequestType) -> Unit
) {
    val paddingValues = PaddingValues(horizontal = 16.dp)
    Column {
        MovieHeader(moviesCategoriesListHeaderText, paddingValues) {
            allFilmsClickListener(moviePagingSourceRequestType)
        }
        PagingHorizontalMovieList(moviePagingState, Color.Black, startMovieDetailsListener)
    }
}

@Composable
private fun MovieHeader(
    moviesCategoriesListHeaderText: String,
    paddingValues: PaddingValues,
    allFilmsClickListener: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = moviesCategoriesListHeaderText,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier,
                onClick = { allFilmsClickListener() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.all_text),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    }
}





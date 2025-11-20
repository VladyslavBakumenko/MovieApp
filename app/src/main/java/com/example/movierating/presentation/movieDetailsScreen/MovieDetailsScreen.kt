package com.example.movierating.presentation.movieDetailsScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.movierating.data.restModels.Movie
import com.example.movierating.data.restModels.movieCreditsResponse.Actor
import com.example.movierating.domain.utils.TMDB_IMAGES_BASE_URL
import com.example.movierating.domain.utils.createGenresString
import com.example.movierating.domain.utils.createYouTubeThumbnailImageModel
import com.example.movierating.domain.utils.getPaddingValuesForLazyRowItems
import com.example.movierating.domain.utils.openYouTubeAppWithVideo
import com.example.movierating.presentation.utils.HorizontalMoviesOrActorList
import com.example.movierating.presentation.utils.PagingHorizontalMovieList

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    onStartMovieDetailsListener: (movie: Movie) -> Unit,
    onStartActorDetailsListener: (actor: Actor) -> Unit,
    backPressedClickListener: () -> Unit
) {

    val viewModel = hiltViewModel<MovieDetailsScreenViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.onIntent(MovieDetailsIntent.LoadSimilarMovies(movie.id))
        viewModel.onIntent(MovieDetailsIntent.LoadMovieVideos(movie.id))
        viewModel.onIntent(MovieDetailsIntent.LoadMovieCredits(movie.id))
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val gradientColors = listOf(Color.White, Color.Black)
        var sizeImage by remember { mutableStateOf(IntSize.Zero) }
        val topGradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = sizeImage.height.toFloat() / 10,
            endY = 0f
        )
        val bottomGradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = sizeImage.height.toFloat() / 3,
            endY = sizeImage.height.toFloat()
        )

        LazyColumn(Modifier.fillMaxSize(), content = {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenHeightDp.dp / 100 * 75)
                        .background(topGradient)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Brush.verticalGradient(gradientColors))
                            .onGloballyPositioned {
                                sizeImage = it.size
                            },
                        model = TMDB_IMAGES_BASE_URL + movie.posterPath,
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(bottomGradient)
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(topGradient)
                    )
                }
            }
            item {
                MovieBottomInfo(movie)
            }

            item {
                PagingHorizontalMovieList(
                    moviePagingState = state.similarMovieFlow,
                    Color.White,
                    startMovieDetailsListener = { movie ->
                        onStartMovieDetailsListener(movie)
                    })
            }

            item {
                HorizontalMoviesOrActorList(state.cast, Color.White, startActorDetailsScreen = {
                    onStartActorDetailsListener(it)
                })
            }

            item {
                val context = LocalContext.current
                LazyRow(content = {
                    items(state.movieVideoData.size) { index ->
                        val movieVideoKey = state.movieVideoData[index].key
                        val model = createYouTubeThumbnailImageModel(movieVideoKey)
                        Box(
                            Modifier
                                .padding(
                                    getPaddingValuesForLazyRowItems(
                                        index,
                                        state.movieVideoData.size
                                    )
                                )
                                .border(
                                    BorderStroke(2.dp, Color.Red),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxSize()
                                    .clickable {
                                        openYouTubeAppWithVideo(context, movieVideoKey)
                                    },
                                model = model,
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight
                            )
                            Icon(
                                modifier = Modifier.align(Alignment.Center),
                                imageVector = Icons.Outlined.PlayArrow,
                                contentDescription = null,
                            )
                        }
                    }
                })
            }
        })

        IconButton(modifier = Modifier
            .align(Alignment.TopStart)
            .padding(top = 8.dp, start = 8.dp),
            onClick = {
                backPressedClickListener()
            }) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = null,
                tint = Color(Color.White.value)
            )
        }
    }
}

@Composable
private fun MovieBottomInfo(movie: Movie) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = movie.title, color = Color.White)
        Text(text = movie.releaseDate, color = Color.White)
        Text(text = movie.popularity.toString(), color = Color.White)
        Text(
            text = StringBuilder().createGenresString(movie.genreIds),
            color = Color.White
        )
        Text(
            text = movie.overview,
            color = Color.White
        )
    }
}
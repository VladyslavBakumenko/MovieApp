package com.example.movierating.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.example.movierating.data.restModels.Movie
import com.example.movierating.data.restModels.movieCreditsResponse.Actor
import com.example.movierating.data.restModels.profileImagesResponse.ProfileImageInfo
import com.example.movierating.domain.utils.TMDB_IMAGES_BASE_URL
import com.example.movierating.domain.utils.getPaddingValuesForLazyRowItems
import kotlinx.coroutines.flow.Flow


@Composable
fun PagingHorizontalMovieList(
    moviePagingState: Flow<PagingData<Movie>>,
    rowItemNameTextColor: Color = Color.Black,
    startMovieDetailsListener: (movie: Movie) -> Unit
) {
    val movieList = moviePagingState.collectAsLazyPagingItems()
    val isInitialLoading = movieList.loadState.refresh is LoadState.Loading

    if (isInitialLoading) {
        MoviesHorizontalSkeleton()
    } else {
        LazyRow {
            items(
                count = movieList.itemCount,
                key = movieList.itemKey(),
                contentType = movieList.itemContentType()
            ) { index ->
                movieList[index]?.let {
                    MovieActorItem(
                        it,
                        getPaddingValuesForLazyRowItems(index, movieList.itemCount),
                        rowItemNameTextColor
                    ) { movie ->
                        startMovieDetailsListener(movie)
                    }
                }
            }
        }
    }
}

@Composable
fun <T> HorizontalMoviesOrActorList(
    movieOrActorsList: List<T>,
    rowItemNameTextColor: Color = Color.Black,
    startMovieDetailsScreen: ((movie: Movie) -> Unit)? = null,
    startActorDetailsScreen: ((actor: Actor) -> Unit)? = null
) {
    LazyRow(content = {
        items(movieOrActorsList.size) { index ->
            val paddingValues = getPaddingValuesForLazyRowItems(index, movieOrActorsList.size)
            when (movieOrActorsList.firstOrNull()) {
                is Movie -> {
                    val movie = movieOrActorsList[index] as Movie
                    MovieActorItem(
                        movie,
                        paddingValues,
                        rowItemNameTextColor,
                        onClick = {
                            startMovieDetailsScreen?.invoke(movie)
                        })
                }

                is Actor -> {
                    val actor = movieOrActorsList[index] as Actor
                    MovieActorItem(
                        actor,
                        paddingValues,
                        rowItemNameTextColor,
                        onClick = {
                            startActorDetailsScreen?.invoke(actor)
                        })
                }
            }
        }
    })
}


@Composable
fun MoviesHorizontalSkeleton(
    placeholderCount: Int = 5
) {
    val items = remember(placeholderCount) { List(placeholderCount) { it } }
    LazyRow {
        items(items) { index ->
            MoviePosterSkeleton(
                paddingValues = getPaddingValuesForLazyRowItems(index, items.size)
            )
        }
    }
}

@Composable
fun MoviePosterSkeleton(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp),
    showTitlePlaceholder: Boolean = true
) {
    Column(
        modifier = modifier
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(210.dp)
                .background(shimmerBrush())
        )
        if (showTitlePlaceholder) {
            Spacer(Modifier.size(8.dp))
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(16.dp)
                    .background(shimmerBrush())
            )
        }
    }
}


@Composable
fun <T> MovieActorItem(
    movieOrActor: T,
    horizontalPaddingVales: PaddingValues,
    itemNameTextColor: Color = Color.Black,
    onClick: (item: T) -> Unit
) {

    val showShimmer = remember { mutableStateOf(true) }
    var imagePath = String()
    var name = String()

    when (movieOrActor) {
        is Actor -> {
            imagePath = movieOrActor.profilePath
            name = movieOrActor.name
        }

        is Movie -> {
            imagePath = movieOrActor.posterPath
            name = movieOrActor.title
        }

        is ProfileImageInfo -> {
            imagePath = movieOrActor.filePath
        }
    }

    Box(
        Modifier
            .wrapContentSize()
            .padding(horizontalPaddingVales)
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick(movieOrActor) },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(
                        shimmerBrush(
                            targetValue = 1300f,
                            showShimmer = showShimmer.value
                        )
                    )
                    .width(140.dp)
                    .height(210.dp),
                model = TMDB_IMAGES_BASE_URL + imagePath,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                onSuccess = { showShimmer.value = false }
            )
            Spacer(Modifier.size(8.dp))
            Text(
                modifier = Modifier.width(140.dp),
                text = name,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = itemNameTextColor,
                maxLines = 1
            )
            Spacer(Modifier.size(8.dp))
        }
    }
}
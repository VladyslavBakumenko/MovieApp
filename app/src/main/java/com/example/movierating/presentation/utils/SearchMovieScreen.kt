package com.example.movierating.presentation.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movierating.R
import com.example.movierating.data.restModels.Movie
import com.example.movierating.presentation.allMoviesListScreen.AllMoviesListScreen
import com.example.movierating.presentation.searchMovieScreen.SearchMovieScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMovieScreen(
    startMovieDetailsListener: (movie: Movie) -> Unit,
    onBackPressedListener: () -> Unit
) {
    val viewModel = hiltViewModel<SearchMovieScreenViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold(Modifier.fillMaxSize(), content = { paddingValues ->
        if (state.isLoadingMovies) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        Column {
            SearchMovieTextField(paddingValues, state.searchedMovieTextState) {
                viewModel.changeState(it)
            }
            Spacer(Modifier.size(16.dp))
            AllMoviesListScreen(pagingMoviesFlow = state.movieFlow, startMovieDetailsListener = {
                startMovieDetailsListener(it)
            }, firstPageLoadingListener = {
                viewModel.changeLoadingState(true)
            }, firstPageLoadingSuccessListener = {
                viewModel.changeLoadingState(false)
            })
        }
    }, topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                stringResource(id = R.string.search_movie_text),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
            navigationIcon = {
                IconButton(onClick = { onBackPressedListener() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            })
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMovieTextField(
    paddingValues: PaddingValues,
    textFieldState: String,
    textListener: (text: String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        value = textFieldState,
        onValueChange = {
            textListener(it)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { textListener("") }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null
                )
            }
        },
        maxLines = 1
    )
}


package com.example.movierating.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.movierating.domain.navigation.AppNavGraph
import com.example.movierating.domain.navigation.rememberNavigationState
import com.example.movierating.presentation.actorDetailsScreen.ActorDetailsScreen
import com.example.movierating.presentation.allMoviesListScreen.AllMoviesListScreen
import com.example.movierating.presentation.movieDetailsScreen.MovieDetailsScreen
import com.example.movierating.presentation.movieScreen.MovieScreen
import com.example.movierating.presentation.searchMovieScreen.SearchMovieScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieMainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold { paddingValues ->
        AppNavGraph(navHostController = navigationState.navHostController, movieScreenContent = {
            MovieScreen(paddingValues, startMovieDetailsListener = { movie ->
                navigationState.navigateToDetailsMovieScreen(movie)
            }, allFilmsClickListener = { moviePagingSourceRequestType ->
                navigationState.navigateAllMoviesListScreen(moviePagingSourceRequestType)
            }, searchListener = {
                navigationState.navigateToSearchMovieScreen()
            })
        }, movieDetailsScreenContent = {
            MovieDetailsScreen(
                movie = it,
                onStartMovieDetailsListener = { movie ->
                    navigationState.navigateToDetailsMovieScreen(movie)
                },
                onStartActorDetailsListener = { actor ->
                    navigationState.navigateToActorScreen(actor)
                },
                backPressedClickListener = {
                    navigationState.popBackStack()
                }
            )
        }, actorDetailsScreenContent = { actor ->
            ActorDetailsScreen(actor = actor) {
                navigationState.popBackStack()
            }
        }, allMoviesListScreenContent = { moviePagingSourceRequestType ->
            AllMoviesListScreen(
                startMovieDetailsListener = { movie ->
                    navigationState.navigateToDetailsMovieScreen(movie)
                }
            )
        }, searchMovieContent = {
            SearchMovieScreen(
                startMovieDetailsListener = {
                    navigationState.navigateToDetailsMovieScreen(it)
                }, onBackPressedListener = {
                    navigationState.popBackStack()
                })
        })
    }
}
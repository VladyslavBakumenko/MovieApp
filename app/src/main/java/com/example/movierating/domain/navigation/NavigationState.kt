package com.example.movierating.domain.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movierating.data.restModels.Movie
import com.example.movierating.data.restModels.movieCreditsResponse.Actor
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType

class NavigationState(val navHostController: NavHostController) {

    fun popBackStack() {
        navHostController.popBackStack()
    }

    fun navigateToDetailsMovieScreen(movie: Movie) {
        navHostController.navigate(Screen.MovieDetailsScreen.getRouteWithArgs(movie))
    }

    fun navigateToActorScreen(actor: Actor) {
        navHostController.navigate(Screen.ActorDetailsScreen.getRouteWithArgs(actor))
    }

    fun navigateAllMoviesListScreen(moviePagingSourceRequestType: MoviePagingSourceRequestType) {
        navHostController.navigate(Screen.AllMoviesListScreen.getRouteWithArgs(moviePagingSourceRequestType))
    }

    fun navigateToSearchMovieScreen() {
        navHostController.navigate(Screen.SearchMovieScreen.route)
    }
}

@Composable
fun rememberNavigationState(navHostController: NavHostController = rememberNavController()): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}
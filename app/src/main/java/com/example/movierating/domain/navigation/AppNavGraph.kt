package com.example.movierating.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movierating.data.restModels.Movie
import com.example.movierating.data.restModels.movieCreditsResponse.Actor
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import com.example.movierating.domain.utils.parcelable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    movieScreenContent: @Composable () -> Unit,
    movieDetailsScreenContent: @Composable (movie: Movie) -> Unit,
    actorDetailsScreenContent: @Composable (actor: Actor) -> Unit,
    allMoviesListScreenContent: @Composable (moviePagingSourceRequestType: MoviePagingSourceRequestType) -> Unit,
    searchMovieContent: @Composable () -> Unit
) {
    NavHost(navController = navHostController, startDestination = Screen.MovieScreen.route) {

        composable(Screen.MovieScreen.route) {
            movieScreenContent()
        }

        composable(
            route = Screen.MovieDetailsScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_MOVIE) {
                    type = Movie.navigationType
                }
            )
        ) {
            val movie =
                it.arguments?.parcelable<Movie>(Screen.KEY_MOVIE)
                    ?: throw RuntimeException("args is null")
            movieDetailsScreenContent(movie)
        }

        composable(
            route = Screen.ActorDetailsScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_ACTOR) {
                    type = Actor.navigationType
                }
            )
        ) {
            val actor =
                it.arguments?.parcelable<Actor>(Screen.KEY_ACTOR)
                    ?: throw RuntimeException("args is null")
            actorDetailsScreenContent(actor)
        }

        composable(
            route = Screen.AllMoviesListScreen.route,
            arguments = listOf(
                navArgument(Screen.KEY_MOVIE_PAGING_SOURCE_REQUEST_TYPE) {
                    type = NavType.EnumType(MoviePagingSourceRequestType::class.java)
                }
            )
        ) {
            val moviePagingSourceRequestType =
                it.arguments?.parcelable<MoviePagingSourceRequestType>(Screen.KEY_MOVIE_PAGING_SOURCE_REQUEST_TYPE)
                    ?: throw RuntimeException("args is null")
            allMoviesListScreenContent(moviePagingSourceRequestType)
        }

        composable(Screen.SearchMovieScreen.route) {
            searchMovieContent()
        }
    }
}

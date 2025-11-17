package com.example.movierating.domain.navigation

import com.example.movierating.data.restModels.Movie
import com.example.movierating.data.restModels.movieCreditsResponse.Actor
import com.example.movierating.domain.network.paging.MoviePagingSourceRequestType
import com.example.movierating.domain.utils.encode
import com.google.gson.Gson

sealed class Screen(val route: String) {

    object MovieScreen : Screen(ROUTE_MOVIE_SCREEN)

    object MovieDetailsScreen : Screen(ROUTE_MOVIE_DETAILS) {

        private const val ROUTE_FOR_ARGS = "movie_details"

        fun getRouteWithArgs(movie: Movie): String {
            val movieJson = Gson().toJson(movie)
            return "$ROUTE_FOR_ARGS/${movieJson.encode()}"
        }
    }

    object ActorDetailsScreen : Screen(ROUTE_ACTOR_DETAILS) {

        private const val ROUTE_FOR_ARGS = "actor_details"

        fun getRouteWithArgs(actor: Actor): String {
            val actorJson = Gson().toJson(actor)
            return "${ROUTE_FOR_ARGS}/${actorJson.encode()}"
        }
    }

    object AllMoviesListScreen : Screen(ROUTE_ALL_MOVIES_LIST_SCREEN) {
        private const val ROUTE_FOR_ARGS = "all_movies_list_screen"

        fun getRouteWithArgs(moviePagingSourceRequestType: MoviePagingSourceRequestType): String {
            return "$ROUTE_FOR_ARGS/${moviePagingSourceRequestType}"
        }
    }

    object SearchMovieScreen : Screen(ROUTE_SEARCH_MOVIE_SCREEN)

    companion object {
        const val KEY_MOVIE = "movie"
        const val KEY_ACTOR = "actor"
        const val KEY_MOVIE_PAGING_SOURCE_REQUEST_TYPE = "movie_paging_source_request_type"

        const val ROUTE_MOVIE_SCREEN = "route_movie_screen"
        const val ROUTE_MOVIE_DETAILS = "movie_details/{$KEY_MOVIE}"
        const val ROUTE_ACTOR_DETAILS = "actor_details/{$KEY_ACTOR}"
        const val ROUTE_ALL_MOVIES_LIST_SCREEN =
            "all_movies_list_screen/{$KEY_MOVIE_PAGING_SOURCE_REQUEST_TYPE}"
        const val ROUTE_SEARCH_MOVIE_SCREEN = "all_search_movie_screen"
    }
}

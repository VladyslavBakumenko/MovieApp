package com.example.movierating.data.sealed

import com.example.movierating.R
import com.example.movierating.domain.navigation.Screen

sealed class NavigationDrawerMenuItem(
    val screen: Screen,
    val titleResId: Int,
    var selected: Boolean = false
) {

    object Favorite : NavigationDrawerMenuItem(
        screen = Screen.FavoriteMoviesScreen,
        titleResId = R.string.navigation_drawer_item_favorite,
    )

    object Watchlist : NavigationDrawerMenuItem(
        screen = Screen.WatchlistMoviesScreen,
        titleResId = R.string.navigation_drawer_item_watchlist,
    )

    object Exit : NavigationDrawerMenuItem(
        screen = Screen.WatchlistMoviesScreen,
        titleResId = R.string.navigation_drawer_item_exit,
    )
}
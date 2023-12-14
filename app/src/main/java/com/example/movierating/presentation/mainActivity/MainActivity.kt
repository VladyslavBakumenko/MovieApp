package com.example.movierating.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movierating.presentation.MovieMainScreen
import com.example.movierating.presentation.utils.HideSystemBars
import com.example.movierating.ui.theme.MovieRatingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HideSystemBars()
            MovieRatingTheme {
                MovieMainScreen()
            }
        }
    }
}

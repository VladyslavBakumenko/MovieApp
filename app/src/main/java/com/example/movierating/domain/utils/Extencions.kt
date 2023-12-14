package com.example.movierating.domain.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.example.movierating.data.MovieGenges

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun String.encode(): String = Uri.encode(this)

fun StringBuilder.createGenresString(genresIds: List<Int>): String {
    genresIds.forEachIndexed { index, genreId ->
        MovieGenges.values().forEach { movieGenre ->
            if (genreId == movieGenre.id) {
                this.append(movieGenre.name.plus(","))
                if (index == genresIds.size) {
                    this.substring(0, this.length - 1)
                }
            }
        }
    }
    return this.toString()
}

fun getPaddingValuesForLazyRowItems(itemIndex: Int, itemListSize: Int): PaddingValues {
    val startPadding = if (itemIndex == 0) 16.dp else 4.dp
    val endPadding = if (itemIndex == itemListSize) 16.dp else 4.dp
    return PaddingValues(start = startPadding, end = endPadding)
}

fun openYouTubeAppWithVideo(context: Context, videoKey: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$YOUTUBE_VIDEO_BASE_URL$videoKey"))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .setPackage(YOUTUBE_APP_PACKAGE)
    context.startActivities(arrayOf(intent))
}

fun createYouTubeThumbnailImageModel(key: String): String {
    return "$YOUTUBE_BASE_IMG_URL$key$YOUTUBE_IMG_END_URL"
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}


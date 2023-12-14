package com.example.movierating.data.restModels

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.movierating.domain.utils.parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("adult")
    var adult: Boolean? = null,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @SerializedName("genre_ids")
    var genreIds: ArrayList<Int> = arrayListOf(),

    @SerializedName("id")
    var id: Int,

    @SerializedName("original_language")
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    var originalTitle: String? = null,

    @SerializedName("overview")
    var overview: String,

    @SerializedName("popularity")
    var popularity: Double? = null,

    @SerializedName("poster_path")
    var posterPath: String,

    @SerializedName("release_date")
    var releaseDate: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("video")
    var video: Boolean,

    @SerializedName("vote_average")
    var voteAverage: Double,

    @SerializedName("vote_count")
    var voteCount: Int
): Parcelable {

    companion object {
        val navigationType: NavType<Movie> = object : NavType<Movie>(false) {
            override fun get(bundle: Bundle, key: String): Movie? {
                return bundle.parcelable(key)
            }

            override fun parseValue(value: String): Movie {
                return Gson().fromJson(value, Movie::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Movie) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
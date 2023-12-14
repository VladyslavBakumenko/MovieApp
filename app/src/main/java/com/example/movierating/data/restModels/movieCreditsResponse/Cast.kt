package com.example.movierating.data.restModels.movieCreditsResponse

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.movierating.domain.utils.parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("gender") var gender: Int,
    @SerializedName("id") var id: Int,
    @SerializedName("known_for_department") var knownForDepartment: String? = null,
    @SerializedName("name") var name: String,
    @SerializedName("original_name") var originalName: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("profile_path") var profilePath: String,
    @SerializedName("cast_id") var castId: Int? = null,
    @SerializedName("character") var character: String? = null,
    @SerializedName("credit_id") var creditId: String? = null,
    @SerializedName("order") var order: Int? = null
): Parcelable {

    companion object {
        val navigationType: NavType<Actor> = object : NavType<Actor>(false) {
            override fun get(bundle: Bundle, key: String): Actor? {
                return bundle.parcelable(key)
            }

            override fun parseValue(value: String): Actor {
                return Gson().fromJson(value, Actor::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Actor) {
                bundle.putParcelable(key, value)
            }
        }
    }
}

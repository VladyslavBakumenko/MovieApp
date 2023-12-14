package com.example.movierating.data.restModels.personDetailsResponse

import com.google.gson.annotations.SerializedName

data class PersonDetailsResponse(
    @SerializedName("adult") var adult: Boolean?,
    @SerializedName("also_known_as") var alsoKnownAs: List<String>,
    @SerializedName("biography") var biography: String,
    @SerializedName("birthday") var birthday: String,
    @SerializedName("deathday") var deathday: String? = null,
    @SerializedName("gender") var gender: Int,
    @SerializedName("homepage") var homepage: String,
    @SerializedName("id") var id: Int,
    @SerializedName("imdb_id") var imdbId: String,
    @SerializedName("known_for_department") var knownForDepartment: String,
    @SerializedName("name") var name: String,
    @SerializedName("place_of_birth") var placeOfBirth: String,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("profile_path") var profilePath: String
)
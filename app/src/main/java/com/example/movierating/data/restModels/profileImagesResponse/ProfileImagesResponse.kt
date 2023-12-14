package com.example.movierating.data.restModels.profileImagesResponse

import com.google.gson.annotations.SerializedName

data class ProfileImagesResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("profiles") var profiles: List<ProfileImageInfo>
)
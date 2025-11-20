package com.example.movierating.presentation.actorDetailsScreen

import com.example.movierating.data.restModels.personDetailsResponse.PersonDetailsResponse
import com.example.movierating.data.restModels.profileImagesResponse.ProfileImageInfo

data class ActorDetailsScreenState(
    val isLoading: Boolean = false,
    val profileDetailsResponse: PersonDetailsResponse? = null,
    val profileImageInfoList: List<ProfileImageInfo> = emptyList(),
    val error: String? = null
)
package com.example.movierating.presentation.actorDetailsScreen

import com.example.movierating.data.restModels.personDetailsResponse.PersonDetailsResponse
import com.example.movierating.data.restModels.profileImagesResponse.ProfileImageInfo

data class ActorDetailsScreenState(
    val profileDetailsResponse: PersonDetailsResponse? = null,
    val profileImageInfoList: List<ProfileImageInfo> = listOf()
)
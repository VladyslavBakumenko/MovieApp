package com.example.movierating.presentation.actorDetailsScreen

sealed class ActorDetailsIntent {
    data class LoadActorDetails(val personId: Int) : ActorDetailsIntent()
    data class LoadActorImages(val personId: Int) : ActorDetailsIntent()
}
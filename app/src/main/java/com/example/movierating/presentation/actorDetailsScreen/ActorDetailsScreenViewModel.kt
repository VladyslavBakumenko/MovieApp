package com.example.movierating.presentation.actorDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierating.domain.network.exceptionHendling.SallyResponseResource
import com.example.movierating.domain.network.usecases.GetPersonDetailsUseCase
import com.example.movierating.domain.network.usecases.GetPersonImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsScreenViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getPersonImagesUseCase: GetPersonImagesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ActorDetailsScreenState())
    val state = _state.asStateFlow()

    fun getActorImages(personId: Int) {
        viewModelScope.launch {
            getPersonImagesUseCase(personId).collect {
                when (it) {
                    is SallyResponseResource.Error -> {

                    }

                    is SallyResponseResource.Loading -> {

                    }

                    is SallyResponseResource.Success -> {
                        _state.update { previousState ->
                            previousState.copy(profileImageInfoList = it.data.body()?.profiles!!)
                        }
                    }
                }
            }
        }
    }

    fun getActorDetails(personId: Int) {
        viewModelScope.launch {
            getPersonDetailsUseCase(personId).collect {
                when (it) {
                    is SallyResponseResource.Error -> {

                    }

                    is SallyResponseResource.Loading -> {

                    }

                    is SallyResponseResource.Success -> {
                        _state.update { previousState ->
                            previousState.copy(profileDetailsResponse = it.data.body())
                        }
                    }
                }
            }
        }
    }
}
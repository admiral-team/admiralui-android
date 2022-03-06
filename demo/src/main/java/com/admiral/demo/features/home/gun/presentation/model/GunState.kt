package com.admiral.demo.features.home.gun.presentation.model

sealed class GunState {
    data class SearchState(
        val idTemplate: String
    ): GunState()

    object LoadingState: GunState()

    data class ErrorState(
        val description: String
    ): GunState()

    data class SuccessState(
        val gunTemplate: GunTemplate
    ): GunState()
}
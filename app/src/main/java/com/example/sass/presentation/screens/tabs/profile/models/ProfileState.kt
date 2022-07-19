package com.example.sass.presentation.screens.tabs.profile.models

sealed class ProfileState {
    object Default : ProfileState()

    object SingOutError : ProfileState()

    object IncorrectToken: ProfileState()

    object SigningOut: ProfileState()
    object SignedOut: ProfileState()
}
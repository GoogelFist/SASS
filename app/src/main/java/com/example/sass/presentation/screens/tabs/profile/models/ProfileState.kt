package com.example.sass.presentation.screens.tabs.profile.models

sealed class ProfileState {
    object Default : ProfileState()

    object SingOutErrorState : ProfileState()

    object IncorrectTokenState: ProfileState()

    object SigningOutState: ProfileState()
    object SignedOutState: ProfileState()
}
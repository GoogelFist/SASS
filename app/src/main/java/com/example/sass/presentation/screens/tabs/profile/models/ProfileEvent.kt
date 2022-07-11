package com.example.sass.presentation.screens.tabs.profile.models

sealed class ProfileEvent {
    object OnLoadUserInfo: ProfileEvent()

    object OnSignOut : ProfileEvent()
    object OnDefaultState: ProfileEvent()
}

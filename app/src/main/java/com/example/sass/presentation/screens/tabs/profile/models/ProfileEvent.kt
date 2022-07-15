package com.example.sass.presentation.screens.tabs.profile.models

sealed class ProfileEvent {
    object OnSignOut : ProfileEvent()

    object OnInit: ProfileEvent()
}

package com.example.sass.presentation.screens.splash.models

sealed class SplashState {

    data class LaunchingMainScreen(val isSignedIn: Boolean) : SplashState()
}
package com.example.sass.presentation.screens.tabs.main.models

sealed class MainState {
//    object Default : MainState()

    object Loading: MainState()
    object ErrorLoaded: MainState()

    object IncorrectToken: MainState()

    object Loaded : MainState()

    object RefreshedError: MainState()
}

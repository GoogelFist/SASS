package com.example.sass.presentation.screens.tabs.main.models

sealed class MainEvent {

    object OnRefresh : MainEvent()
    object OnLoadPictures : MainEvent()

    data class OnAddToFavorite(val id: String) : MainEvent()
    data class OnRemoveFromFavorite(val id: String) : MainEvent()
}

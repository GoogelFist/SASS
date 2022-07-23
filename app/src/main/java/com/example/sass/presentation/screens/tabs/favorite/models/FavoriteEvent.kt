package com.example.sass.presentation.screens.tabs.favorite.models

sealed class FavoriteEvent {
    data class OnRemoveFromFavorite(val id: String) : FavoriteEvent()

    object OnUpdateData : FavoriteEvent()
}

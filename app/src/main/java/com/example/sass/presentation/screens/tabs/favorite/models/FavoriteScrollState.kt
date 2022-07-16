package com.example.sass.presentation.screens.tabs.favorite.models

sealed class FavoriteScrollState {
    object AddedFavorite : FavoriteScrollState()
    object RemovedFavorite : FavoriteScrollState()
    object Default : FavoriteScrollState()
}

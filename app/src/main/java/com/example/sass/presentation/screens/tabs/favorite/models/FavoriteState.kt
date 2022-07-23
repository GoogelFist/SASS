package com.example.sass.presentation.screens.tabs.favorite.models

sealed class FavoriteState {
    object Empty : FavoriteState()
    object Default : FavoriteState()
}
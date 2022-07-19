package com.example.sass.presentation.screens.tabs.main.search.models

sealed class SearchEvent {
    object OnSetDefaultState : SearchEvent()

    data class OnSearchPictures(val text: String) : SearchEvent()

    data class OnAddToFavorite(val id: String) : SearchEvent()
    data class OnRemoveFromFavorite(val id: String) : SearchEvent()
}

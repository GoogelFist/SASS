package com.example.sass.presentation.screens.tabs.main.search.models

sealed class SearchState {
    object Default: SearchState()

    object Found : SearchState()
    object NotFound: SearchState()
}

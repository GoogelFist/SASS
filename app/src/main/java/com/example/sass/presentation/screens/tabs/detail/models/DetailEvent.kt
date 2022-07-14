package com.example.sass.presentation.screens.tabs.detail.models

sealed class DetailEvent {
    data class OnGetDetailPicture(val id: String) : DetailEvent()
}

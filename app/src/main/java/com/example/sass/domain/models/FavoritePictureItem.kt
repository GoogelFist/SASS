package com.example.sass.domain.models


data class FavoritePictureItem(
    val id: String = "",
    val photoUrl: String = "",
    val title: String = "",
    val content: String = "",
    val publicationDate: String = "",
    val isFavorite: Boolean = false
)
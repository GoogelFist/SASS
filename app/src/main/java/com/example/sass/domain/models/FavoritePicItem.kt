package com.example.sass.domain.models


data class FavoritePicItem(
    val id: String = "",
    val photoUrl: String = "",
    val title: String = "",
    val content: String = "",
    val publicationDate: Long = DEFAULT_PUBLICATION_DATE,
    val isFavorite: Boolean = false
)

private const val DEFAULT_PUBLICATION_DATE = 0L
package com.example.sass.domain

import com.example.sass.domain.models.PicturesItem

interface PicturesRepository {

    suspend fun loadPictures()

    suspend fun getPicturesItems(): List<PicturesItem>

    suspend fun clearData()

    suspend fun addToFavorite(id: String)

    suspend fun removedFromFavorite(id: String)
}
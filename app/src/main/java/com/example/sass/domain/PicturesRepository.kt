package com.example.sass.domain

import com.example.sass.domain.models.FavoritePicItem
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem

interface PicturesRepository {

    suspend fun loadPictures()

    suspend fun getPicturesItems(): List<PicturesItem>

    suspend fun searchPicsItems(searchText: String): List<PicturesItem>

    suspend fun getFavoritePicsItems(): List<FavoritePicItem>

    suspend fun clearData()

    suspend fun addToFavorite(id: String)

    suspend fun removedFromFavorite(id: String)

    suspend fun getPictureDetail(id: String): PictureDetail
}
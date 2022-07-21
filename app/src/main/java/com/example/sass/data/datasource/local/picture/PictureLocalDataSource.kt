package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PictureDbEntity
import com.example.sass.domain.models.FavoritePictureItem
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem

interface PictureLocalDataSource {

    suspend fun savePicturesDbEntity(picturesDbEntity: List<PictureDbEntity>)

    suspend fun findPicturesItems(searchText: String): List<PicturesItem>

    suspend fun loadPicturesItems(): List<PicturesItem>

    suspend fun getPictureDetail(id: String): PictureDetail

    suspend fun deleteAllPictures()

    suspend fun loadFavoritePictureItems(): List<FavoritePictureItem>

    suspend fun addPictureToFavorite(id: String)

    suspend fun removePictureFromFavorite(id: String)

    suspend fun deleteAllPicturesFromFavorites()
}
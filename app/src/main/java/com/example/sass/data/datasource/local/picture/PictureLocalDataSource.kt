package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PictureFavoriteDao

interface PictureLocalDataSource {

    suspend fun savePictureFavorite(pictureFavoriteDao: PictureFavoriteDao)

    suspend fun deletePictureFavorite(id: String)

    suspend fun loadPicturesFavorites(): List<PictureFavoriteDao>

    suspend fun deleteAll()
}
package com.example.sass.data.datasource.local.favoritepicture

import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.domain.models.FavoritePictureItem

interface PictureFavoritesLocalDataSource {

    suspend fun saveFavoritePicture(pictureDao: PictureDao)

    suspend fun loadFavoritePicturesItems(): List<FavoritePictureItem>

    suspend fun loadFavoritePicturesIds(): List<String>

    suspend fun deleteFavoritePictureDao(id: String)

    suspend fun deleteAllFavoritePicturesDao()
}
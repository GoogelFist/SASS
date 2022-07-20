package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.domain.models.FavoritePictureItem
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem

interface PictureLocalDataSource {

    suspend fun saveFavoritePicture(id: String)

    suspend fun loadFavoritePicturesItems(): List<FavoritePictureItem>

    suspend fun deleteFavoritePictureDao(id: String)

    suspend fun deleteAllFavoritePicturesDao()

    suspend fun savePicturesDao(picturesDaoList: List<PictureDao>)

    suspend fun getPictureDaoById(id: String): PictureDao

    suspend fun deleteAllPicturesDao()

    suspend fun findPicturesItems(searchText: String): List<PicturesItem>

    suspend fun loadPicturesItems(): List<PicturesItem>

    suspend fun getPictureDetail(id: String): PictureDetail
}
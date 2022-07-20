package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem

interface PictureLocalDataSource {

    suspend fun savePicturesDao(picturesDaoList: List<PictureDao>)

    suspend fun getPictureDaoById(id: String): PictureDao

    suspend fun deleteAllPicturesDao()

    suspend fun findPicturesItems(searchText: String, favoritesIds: List<String>): List<PicturesItem>

    suspend fun loadPicturesItems(favoritesIds: List<String>): List<PicturesItem>

    suspend fun getPictureDetail(id: String): PictureDetail
}
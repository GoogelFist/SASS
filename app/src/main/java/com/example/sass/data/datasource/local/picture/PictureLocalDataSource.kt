package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PicCommonDao
import com.example.sass.domain.models.FavoritePicItem
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem

interface PictureLocalDataSource {

    suspend fun saveFavoritePic(id: String)

    suspend fun deleteFavoritePicDao(id: String)

    suspend fun loadFavoritePicsItems(): List<FavoritePicItem>

    suspend fun deleteAllFavoritePics()

    suspend fun savePicsResponseDto(picsCommonDto: List<PicCommonDao>)

    suspend fun loadPicsItems(): List<PicturesItem>

    suspend fun findPicCommonDtoById(id: String): PicCommonDao

    suspend fun deleteAllPicsCommonDao()

    suspend fun getPictureDetail(id: String): PictureDetail
}
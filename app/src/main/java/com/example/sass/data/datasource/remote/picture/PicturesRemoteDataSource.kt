package com.example.sass.data.datasource.remote.picture

import com.example.sass.data.datasource.local.picture.models.PictureFavoriteDao
import com.example.sass.domain.models.PicturesItem

interface PicturesRemoteDataSource {

    suspend fun loadPictures(token: String)

    suspend fun getPicturesItems(favorites: List<PictureFavoriteDao>): List<PicturesItem>
}
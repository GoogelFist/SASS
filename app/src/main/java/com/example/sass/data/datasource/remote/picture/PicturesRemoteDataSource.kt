package com.example.sass.data.datasource.remote.picture

import com.example.sass.data.datasource.local.picture.models.PictureDbEntity

interface PicturesRemoteDataSource {

    suspend fun loadPictures(token: String): List<PictureDbEntity>
}
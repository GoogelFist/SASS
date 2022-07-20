package com.example.sass.data.datasource.remote.picture

import com.example.sass.data.datasource.local.picture.models.PictureDao

interface PicturesRemoteDataSource {

    suspend fun loadPictures(token: String): List<PictureDao>
}
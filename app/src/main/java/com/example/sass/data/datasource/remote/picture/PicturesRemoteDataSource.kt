package com.example.sass.data.datasource.remote.picture

import com.example.sass.data.datasource.local.picture.models.PicCommonDao

interface PicturesRemoteDataSource {

    suspend fun loadPictures(token: String): List<PicCommonDao>
}
package com.example.sass.data.datasource.remote.picture

import com.example.sass.domain.models.PicturesItem

interface PicturesRemoteDataSource {

    suspend fun loadPictures(token: String): List<PicturesItem>
}
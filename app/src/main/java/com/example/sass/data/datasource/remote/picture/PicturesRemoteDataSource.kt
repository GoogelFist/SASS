package com.example.sass.data.datasource.remote.picture

import com.example.sass.data.datasource.remote.picture.models.PicturesListResponse
import retrofit2.Response

interface PicturesRemoteDataSource {

    suspend fun loadPictures(token: String): Response<PicturesListResponse>
}
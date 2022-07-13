package com.example.sass.data.datasource.remote.picture

import com.example.sass.data.datasource.remote.picture.models.PicturesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface PicturesRetrofitService {

    @GET("picture")
    @Headers("accept: application/json")
    suspend fun loadPictures(@Header("Authorization") token: String): Response<PicturesResponse>
}
package com.example.sass.data.datasource.remote.picture

import com.example.sass.data.datasource.remote.picture.models.PicturesListResponse
import retrofit2.Response
import javax.inject.Inject

class PicturesRetrofitDataSourceImpl @Inject constructor(private val picturesRetrofitService: PicturesRetrofitService) :
    PicturesRemoteDataSource {
    override suspend fun loadPictures(token: String): Response<PicturesListResponse> {
        return picturesRetrofitService.loadPictures(token)
    }
}
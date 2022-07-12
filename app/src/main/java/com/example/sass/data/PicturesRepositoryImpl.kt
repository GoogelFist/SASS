package com.example.sass.data

import com.example.sass.data.datasource.local.token.TokenLocalDataSource
import com.example.sass.data.datasource.local.user.UserLocalDataSource
import com.example.sass.data.datasource.remote.picture.PicturesRemoteDataSource
import com.example.sass.data.mapper.PicturesMapper
import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val picturesRemoteDataSource: PicturesRemoteDataSource
) : PicturesRepository {

    private val picturesItems = mutableListOf<PicturesItem>()

    override suspend fun loadPictures() {

        val token = tokenLocalDataSource.loadAuthToken()
        val loadPictures = picturesRemoteDataSource.loadPictures(token)

        picturesItems.clear()
        picturesItems.addAll(loadPictures)
    }

    override suspend fun getPictures(): List<PicturesItem> {
        return picturesItems.toList()
    }
}
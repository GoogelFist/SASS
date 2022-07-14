package com.example.sass.data

import com.example.sass.data.datasource.local.picture.PictureLocalDataSource
import com.example.sass.data.datasource.local.token.TokenLocalDataSource
import com.example.sass.data.datasource.remote.picture.PicturesRemoteDataSource
import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.FavoritePicItem
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val picturesRemoteDataSource: PicturesRemoteDataSource,
    private val pictureLocalDataSource: PictureLocalDataSource
) : PicturesRepository {

    override suspend fun loadPictures() {
        val token = tokenLocalDataSource.loadAuthToken()
        val loadPictures = picturesRemoteDataSource.loadPictures(token)
        pictureLocalDataSource.savePicsResponseDto(loadPictures)
    }

    override suspend fun getPicturesItems(): List<PicturesItem> {
        return pictureLocalDataSource.loadPicsItems()
    }

    override suspend fun getFavoritePicsItems(): List<FavoritePicItem> {
        return pictureLocalDataSource.loadFavoritePicsItems()
    }

    override suspend fun clearData() {
        pictureLocalDataSource.deleteAllFavoritePics()
        pictureLocalDataSource.deleteAllPicsCommonDao()
    }

    override suspend fun addToFavorite(id: String) {
        pictureLocalDataSource.saveFavoritePic(id)
    }

    override suspend fun removedFromFavorite(id: String) {
        pictureLocalDataSource.deleteFavoritePicDao(id)
    }

    override suspend fun getPictureDetail(id: String): PictureDetail {
        return pictureLocalDataSource.getPictureDetail(id)
    }
}
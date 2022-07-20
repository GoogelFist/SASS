package com.example.sass.data

import com.example.sass.data.datasource.local.picture.PictureLocalDataSource
import com.example.sass.data.datasource.local.token.TokenLocalDataSource
import com.example.sass.data.datasource.remote.picture.PicturesRemoteDataSource
import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.FavoritePictureItem
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
        pictureLocalDataSource.savePicturesDao(loadPictures)
    }

    override suspend fun getPicturesItems(): List<PicturesItem> {
        return pictureLocalDataSource.loadPicturesItems()
    }

    override suspend fun searchPicsItems(searchText: String): List<PicturesItem> {
        return pictureLocalDataSource.findPicturesItems(searchText)
    }

    override suspend fun getFavoritePicsItems(): List<FavoritePictureItem> {
        return pictureLocalDataSource.loadFavoritePicturesItems()
    }

    override suspend fun clearData() {
        pictureLocalDataSource.deleteAllFavoritePicturesDao()
        pictureLocalDataSource.deleteAllPicturesDao()
    }

    override suspend fun addToFavorite(id: String) {
        pictureLocalDataSource.saveFavoritePicture(id)
    }

    override suspend fun removedFromFavorite(id: String) {
        pictureLocalDataSource.deleteFavoritePictureDao(id)
    }

    override suspend fun getPictureDetail(id: String): PictureDetail {
        return pictureLocalDataSource.getPictureDetail(id)
    }
}
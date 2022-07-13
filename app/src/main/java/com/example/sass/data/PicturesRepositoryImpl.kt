package com.example.sass.data

import com.example.sass.data.datasource.local.picture.PictureLocalDataSource
import com.example.sass.data.datasource.local.picture.models.PictureFavoriteDao
import com.example.sass.data.datasource.local.token.TokenLocalDataSource
import com.example.sass.data.datasource.remote.picture.PicturesRemoteDataSource
import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val picturesRemoteDataSource: PicturesRemoteDataSource,
    private val pictureLocalDataSource: PictureLocalDataSource
) : PicturesRepository {

    private lateinit var picturesItems: List<PicturesItem>


    override suspend fun loadPictures() {
        val token = tokenLocalDataSource.loadAuthToken()
        picturesRemoteDataSource.loadPictures(token)
    }

    // TODO:
    override suspend fun getPicturesItems(): List<PicturesItem> {
        val favorites = pictureLocalDataSource.loadPicturesFavorites()
        picturesItems = picturesRemoteDataSource.getPicturesItems(favorites)
        return picturesItems.toList()
    }


    override suspend fun clearData() {
        pictureLocalDataSource.deleteAll()
    }

    override suspend fun addToFavorite(id: String) {
        val nowDate = System.currentTimeMillis()
        pictureLocalDataSource.savePictureFavorite(PictureFavoriteDao(id, nowDate))
    }

    override suspend fun removedFromFavorite(id: String) {
        pictureLocalDataSource.deletePictureFavorite(id)
    }
}
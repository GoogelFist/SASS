package com.example.sass.data

import com.example.sass.data.datasource.local.picture.PictureLocalDataSource
import com.example.sass.data.datasource.local.favoritepicture.PictureFavoritesLocalDataSource
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
    private val pictureLocalDataSource: PictureLocalDataSource,
    private val pictureFavoritesLocalDataSource: PictureFavoritesLocalDataSource
) : PicturesRepository {

    override suspend fun loadPictures() {
        val token = tokenLocalDataSource.loadAuthToken()
        val loadPictures = picturesRemoteDataSource.loadPictures(token)
        pictureLocalDataSource.savePicturesDao(loadPictures)
    }

    override suspend fun getPicturesItems(): List<PicturesItem> {
        val ids = pictureFavoritesLocalDataSource.loadFavoritePicturesIds()
        return pictureLocalDataSource.loadPicturesItems(ids)
    }

    override suspend fun findPicturesItems(searchText: String): List<PicturesItem> {
        val ids = pictureFavoritesLocalDataSource.loadFavoritePicturesIds()
        return pictureLocalDataSource.findPicturesItems(searchText, ids)
    }

    override suspend fun getFavoritePicsItems(): List<FavoritePictureItem> {
        return pictureFavoritesLocalDataSource.loadFavoritePicturesItems()
    }

    override suspend fun clearData() {
        pictureFavoritesLocalDataSource.deleteAllFavoritePicturesDao()
        pictureLocalDataSource.deleteAllPicturesDao()
    }

    override suspend fun addToFavorite(id: String) {
        val pictureDao = pictureLocalDataSource.getPictureDaoById(id)
        pictureFavoritesLocalDataSource.saveFavoritePicture(pictureDao)
    }

    override suspend fun removedFromFavorite(id: String) {
        pictureFavoritesLocalDataSource.deleteFavoritePictureDao(id)
    }

    override suspend fun getPictureDetail(id: String): PictureDetail {
        return pictureLocalDataSource.getPictureDetail(id)
    }
}
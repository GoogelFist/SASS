package com.example.sass.data

import com.example.sass.data.datasource.local.picture.PicturesLocalDataSource
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
    private val picturesLocalDataSource: PicturesLocalDataSource
) : PicturesRepository {

    override suspend fun loadPictures() {
        val token = tokenLocalDataSource.loadAuthToken()
        val loadPictures = picturesRemoteDataSource.loadPictures(token)
        picturesLocalDataSource.savePicturesDbEntity(loadPictures)
    }

    override suspend fun getPicturesItems(): List<PicturesItem> {
        return picturesLocalDataSource.loadPicturesItems()
    }

    override suspend fun findPicturesItems(searchText: String): List<PicturesItem> {
        return picturesLocalDataSource.findPicturesItems(searchText)
    }

    override suspend fun getFavoritePicsItems(): List<FavoritePictureItem> {
        return picturesLocalDataSource.loadFavoritePictureItems()
    }

    override suspend fun clearData() {
        picturesLocalDataSource.deleteAllPicturesFromFavorites()
        picturesLocalDataSource.deleteAllPictures()
    }

    override suspend fun addToFavorite(id: String) {
        picturesLocalDataSource.addPictureToFavorite(id)
    }

    override suspend fun removedFromFavorite(id: String) {
        picturesLocalDataSource.removePictureFromFavorite(id)
    }

    override suspend fun getPictureDetail(id: String): PictureDetail {
        return picturesLocalDataSource.getPictureDetail(id)
    }
}
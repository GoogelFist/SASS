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

    private lateinit var picturesItems: MutableList<PicturesItem>


    override suspend fun loadPictures() {
        val token = tokenLocalDataSource.loadAuthToken()
        picturesRemoteDataSource.loadPictures(token)
    }

    // TODO:
    override suspend fun getPicturesItems(): List<PicturesItem> {
        val favorites = pictureLocalDataSource.loadPicturesFavorites()
        picturesItems =
            picturesRemoteDataSource.getPicturesItems(favorites) as MutableList<PicturesItem>
        return picturesItems.toList()
    }


    override suspend fun clearData() {
        pictureLocalDataSource.deleteAll()
    }

    override suspend fun addToFavorite(id: String) {
        val nowDate = System.currentTimeMillis()
        pictureLocalDataSource.savePictureFavorite(PictureFavoriteDao(id, nowDate))

        addToFavoriteInPicturesItems(id)
    }

    override suspend fun removedFromFavorite(id: String) {
        pictureLocalDataSource.deletePictureFavorite(id)
        removeFromFavoritePicturesItems(id)
    }

    private fun addToFavoriteInPicturesItems(id: String) {
        val ind = findIndexByPictureId(id)

        if (ind != NOT_FOUND_INDEX) {
            picturesItems[ind] = picturesItems[ind].copy(isFavorite = true)
        }
    }

    private fun removeFromFavoritePicturesItems(id: String) {
        val ind = findIndexByPictureId(id)

        if (ind != NOT_FOUND_INDEX) {
            picturesItems.removeAt(ind)
        }
    }

    private fun findIndexByPictureId(id: String): Int {
        var ind = 0
        picturesItems.forEachIndexed { index, picturesItem ->
            if (picturesItem.id == id) {
                ind = index
                return@forEachIndexed
            }
            ind = NOT_FOUND_INDEX
        }
        return ind
    }

    companion object {
        private const val NOT_FOUND_INDEX = -1
    }
}
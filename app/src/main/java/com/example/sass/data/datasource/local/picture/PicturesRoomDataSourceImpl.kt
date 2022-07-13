package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PictureFavoriteDao
import javax.inject.Inject

class PicturesRoomDataSourceImpl @Inject constructor(
    private val pictureDao: PictureDao
) :
    PictureLocalDataSource {
    override suspend fun savePictureFavorite(pictureFavoriteDao: PictureFavoriteDao) {
        pictureDao.savePictureFavorite(pictureFavoriteDao)
    }

    override suspend fun deletePictureFavorite(id: String) {
        pictureDao.deletePictureFavorite(id)
    }

    override suspend fun loadPicturesFavorites(): List<PictureFavoriteDao> {
        return pictureDao.loadPicturesFavorites()
    }

    override suspend fun deleteAll() {
        pictureDao.deleteAll()
    }
}
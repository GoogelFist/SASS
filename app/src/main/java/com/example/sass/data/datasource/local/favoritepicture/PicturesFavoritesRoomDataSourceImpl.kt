package com.example.sass.data.datasource.local.favoritepicture

import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.data.datasource.mapper.FavoritePicturesMapper
import com.example.sass.domain.models.FavoritePictureItem
import javax.inject.Inject

class PicturesFavoritesRoomDataSourceImpl @Inject constructor(
    private val picturesFavoriteDao: PicturesFavoriteDao,
    private val mapper: FavoritePicturesMapper
) : PictureFavoritesLocalDataSource {

    override suspend fun saveFavoritePicture(pictureDao: PictureDao) {
        val favoritePictureDao = mapper.mapPictureDaoToFavoritePictureDao(pictureDao)
        picturesFavoriteDao.saveFavoritePictureDao(favoritePictureDao)
    }

    override suspend fun loadFavoritePicturesItems(): List<FavoritePictureItem> {
        val favoritePicturesDao = picturesFavoriteDao.loadFavoritePicturesDao()
        return mapper.mapFavoritePicturesDaoToFavoritePicturesItems(favoritePicturesDao)
    }

    override suspend fun loadFavoritePicturesIds(): List<String> {
        return picturesFavoriteDao.loadFavoritePicturesDaoIds()
    }

    override suspend fun deleteFavoritePictureDao(id: String) {
        picturesFavoriteDao.deleteFavoritePictureDao(id)
    }

    override suspend fun deleteAllFavoritePicturesDao() {
        picturesFavoriteDao.deleteAllFavoritePicturesDao()
    }
}
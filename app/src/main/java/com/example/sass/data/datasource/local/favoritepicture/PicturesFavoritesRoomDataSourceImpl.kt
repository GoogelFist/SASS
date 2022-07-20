package com.example.sass.data.datasource.local.favoritepicture

import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.data.datasource.mapper.FavoritePicturesMapper
import com.example.sass.domain.models.FavoritePictureItem
import javax.inject.Inject

class PicturesFavoritesRoomDataSourceImpl @Inject constructor(
    private val picturesDao: PicturesFavoriteDao,
    private val mapper: FavoritePicturesMapper
) : PictureFavoritesLocalDataSource {

    override suspend fun saveFavoritePicture(pictureDao: PictureDao) {
        val favoritePictureDao = mapper.mapPictureDaoToFavoritePictureDao(pictureDao)
        picturesDao.saveFavoritePictureDao(favoritePictureDao)
    }

    override suspend fun loadFavoritePicturesItems(): List<FavoritePictureItem> {
        val favoritePicturesDao = picturesDao.loadFavoritePicturesDao()
        return mapper.mapFavoritePicturesDaoToFavoritePicturesItems(favoritePicturesDao)
    }

    override suspend fun loadFavoritePicturesIds(): List<String> {
        return picturesDao.loadFavoritePicturesDaoIds()
    }

    override suspend fun deleteFavoritePictureDao(id: String) {
        picturesDao.deleteFavoritePictureDao(id)
    }

    override suspend fun deleteAllFavoritePicturesDao() {
        picturesDao.deleteAllFavoritePicturesDao()
    }
}
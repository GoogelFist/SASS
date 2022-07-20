package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.data.mapper.PicturesMapper
import com.example.sass.domain.models.FavoritePictureItem
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRoomDataSourceImpl @Inject constructor(
    private val picturesDao: PicturesDao,
    private val mapper: PicturesMapper
) : PictureLocalDataSource {

    override suspend fun saveFavoritePicture(id: String) {
        val pictureDao = picturesDao.getPictureDaoById(id)
        val favoritePictureDao = mapper.mapPictureDaoToFavoritePictureDao(pictureDao)
        picturesDao.saveFavoritePictureDao(favoritePictureDao)
    }

    override suspend fun loadFavoritePicturesItems(): List<FavoritePictureItem> {
        val favoritePicturesDao = picturesDao.loadFavoritePicturesDao()
        return mapper.mapFavoritePicturesDaoToFavoritePicturesItems(favoritePicturesDao)
    }

    override suspend fun deleteFavoritePictureDao(id: String) {
        picturesDao.deleteFavoritePictureDao(id)
    }

    override suspend fun deleteAllFavoritePicturesDao() {
        picturesDao.deleteAllFavoritePicturesDao()
    }

    override suspend fun savePicturesDao(picturesDaoList: List<PictureDao>) {
        for (pictureDao in picturesDaoList) {
            picturesDao.savePictureDao(pictureDao)
        }
    }

    override suspend fun getPictureDaoById(id: String): PictureDao {
        return picturesDao.getPictureDaoById(id)
    }

    override suspend fun deleteAllPicturesDao() {
        picturesDao.deleteAllPicturesDao()
    }

    override suspend fun findPicturesItems(searchText: String): List<PicturesItem> {
        val searchedPicturesItems = picturesDao.findPicturesDao(searchText)

        val favoritesIds = picturesDao.loadFavoritePicturesDaoIds()

        return mapper.mapPicturesDaoToPicturesItems(searchedPicturesItems, favoritesIds)
    }

    override suspend fun loadPicturesItems(): List<PicturesItem> {
        val picturesItems = picturesDao.loadPicturesDao()

        val favoritesIds = picturesDao.loadFavoritePicturesDaoIds()

        return mapper.mapPicturesDaoToPicturesItems(picturesItems, favoritesIds)
    }

    override suspend fun getPictureDetail(id: String): PictureDetail {
        val pictureDao = picturesDao.getPictureDaoById(id)
        return mapper.mapPictureDaoToPictureDetail(pictureDao)
    }
}
package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.data.datasource.mapper.PicturesMapper
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRoomDataSourceImpl @Inject constructor(
    private val picturesDao: PicturesDao,
    private val mapper: PicturesMapper
) : PictureLocalDataSource {

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

    override suspend fun findPicturesItems(searchText: String, favoritesIds: List<String>): List<PicturesItem> {
        val searchedPicturesItems = picturesDao.findPicturesDao(searchText)

        return mapper.mapPicturesDaoToPicturesItems(searchedPicturesItems, favoritesIds)
    }

    override suspend fun loadPicturesItems(favoritesIds: List<String>): List<PicturesItem> {
        val picturesItems = picturesDao.loadPicturesDao()

        return mapper.mapPicturesDaoToPicturesItems(picturesItems, favoritesIds)
    }

    override suspend fun getPictureDetail(id: String): PictureDetail {
        val pictureDao = picturesDao.getPictureDaoById(id)
        return mapper.mapPictureDaoToPictureDetail(pictureDao)
    }
}
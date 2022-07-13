package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PicCommonDao
import com.example.sass.data.mapper.PicturesMapper
import com.example.sass.domain.models.FavoritePicItem
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRoomDataSourceImpl @Inject constructor(
    private val pictureDao: PictureDao,
    private val mapper: PicturesMapper
) :
    PictureLocalDataSource {
    override suspend fun saveFavoritePic(id: String) {
        val picCommonDao = pictureDao.getPicCommonDaoById(id)
        val favoritePicDao = mapper.mapPicCommonDaoToFavoritePicDao(picCommonDao)
        pictureDao.saveFavoritePicDao(favoritePicDao)
    }

    override suspend fun deleteFavoritePicDao(id: String) {
        pictureDao.deleteFavoritePicDao(id)
    }

    override suspend fun loadFavoritePicsItems(): List<FavoritePicItem> {
        val favoritePicsDao = pictureDao.loadFavoritePicsDao()
        return mapper.mapFavoritePicsDaoToFavoritePicsItems(favoritePicsDao)
    }

    override suspend fun deleteAllFavoritePics() {
        pictureDao.deleteAllFavoritePics()
    }

    override suspend fun savePicsResponseDto(picsCommonDto: List<PicCommonDao>) {
        for (picCommonDao in picsCommonDto) {
            pictureDao.savePicCommonDao(picCommonDao)
        }
    }

    // TODO: separate method with ids
    override suspend fun loadPicsItems(): List<PicturesItem> {
        val picsItems = pictureDao.loadPicsCommonDao()

        val favorites = pictureDao.loadFavoritePicsDao()

        return mapper.mapPicsCommonDaoToPicsItems(picsItems, favorites)
    }

    override suspend fun findPicCommonDtoById(id: String): PicCommonDao {
        return pictureDao.getPicCommonDaoById(id)
    }

    override suspend fun deleteAllPicsCommonDao() {
        pictureDao.deleteAllPicsCommonDao()
    }
}
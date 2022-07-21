package com.example.sass.data.datasource.local.picture

import com.example.sass.data.datasource.local.picture.models.PictureDbEntity
import com.example.sass.domain.models.FavoritePictureItem
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRoomDataSourceImpl @Inject constructor(
    private val picturesDao: PicturesDao
) : PictureLocalDataSource {

    override suspend fun savePicturesDbEntity(picturesDbEntity: List<PictureDbEntity>) {
        picturesDbEntity.forEach { pictureDbEntity -> picturesDao.savePicture(pictureDbEntity) }
    }

    override suspend fun findPicturesItems(searchText: String): List<PicturesItem> {
        return picturesDao.findPictures(searchText).map { it.toPictureItem() }
    }

    override suspend fun loadPicturesItems(): List<PicturesItem> {
        return picturesDao.loadPictures().map { it.toPictureItem() }
    }

    override suspend fun getPictureDetail(id: String): PictureDetail {
        return picturesDao.getPictureById(id).toPictureDetail()
    }

    override suspend fun deleteAllPictures() {
        picturesDao.deleteAllPictures()
    }

    override suspend fun loadFavoritePictureItems(): List<FavoritePictureItem> {
        return picturesDao.loadFavoritePictures().map { it.toFavoritePictureItem() }
    }

    override suspend fun addPictureToFavorite(id: String) {
        picturesDao.addPictureToFavorite(id)
    }

    override suspend fun removePictureFromFavorite(id: String) {
        picturesDao.removePictureFromFavorite(id)
    }

    override suspend fun deleteAllPicturesFromFavorites() {
        picturesDao.deleteAllPicturesFromFavorites()
    }
}
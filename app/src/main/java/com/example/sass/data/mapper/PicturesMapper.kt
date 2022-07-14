package com.example.sass.data.mapper

import com.example.sass.data.datasource.local.picture.models.FavoritePicDao
import com.example.sass.data.datasource.local.picture.models.PicCommonDao
import com.example.sass.data.datasource.remote.picture.models.PictureDto
import com.example.sass.data.datasource.remote.picture.models.PicturesResponse
import com.example.sass.domain.models.FavoritePicItem
import com.example.sass.domain.models.PicturesItem
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PicturesMapper @Inject constructor() {

    fun mapPicsCommonDaoToPicsItems(
        picCommonDao: List<PicCommonDao>,
        favoritesIds: List<String>
    ): List<PicturesItem> {
        val favoritesIdsSet = favoritesIds.toHashSet()
        return picCommonDao.map { mapPicCommonDaoToPicItem(it, favoritesIdsSet) }
    }

    fun mapPicsResponseDtoToPicsResponseDao(picturesResponse: PicturesResponse): List<PicCommonDao> {
        return picturesResponse.map { mapPicsDtoToPicsDao(it) }
    }

    fun mapFavoritePicsDaoToFavoritePicsItems(favoritePicsDao: List<FavoritePicDao>): List<FavoritePicItem> {
        return favoritePicsDao.map { mapFavoritePicDaoToFavoritePicItem(it) }
    }

    fun mapPicCommonDaoToFavoritePicDao(picCommonDao: PicCommonDao): FavoritePicDao {
        return FavoritePicDao(
            id = picCommonDao.id,
            photoUrl = picCommonDao.photoUrl,
            title = picCommonDao.title,
            content = picCommonDao.content,
            publicationDate = picCommonDao.publicationDate,
            isFavorite = true,
            addedTimeMills = System.currentTimeMillis()
        )
    }

    private fun mapPicCommonDaoToPicItem(
        picCommonDao: PicCommonDao,
        favoritesIds: Set<String>
    ): PicturesItem {

        val isFavorite = favoritesIds.contains(picCommonDao.id)

        return PicturesItem(
            picCommonDao.id,
            picCommonDao.photoUrl,
            picCommonDao.title,
            isFavorite
        )
    }

    private fun mapPicsDtoToPicsDao(pictureDto: PictureDto): PicCommonDao {
        return PicCommonDao(
            content = pictureDto.content,
            id = pictureDto.id,
            photoUrl = pictureDto.photoUrl,
            publicationDate = pictureDto.publicationDate,
            title = pictureDto.title
        )
    }

    private fun mapFavoritePicDaoToFavoritePicItem(favoritePicDao: FavoritePicDao): FavoritePicItem {
        return FavoritePicItem(
            id = favoritePicDao.id,
            photoUrl = favoritePicDao.photoUrl,
            title = favoritePicDao.title,
            content = favoritePicDao.content,
            publicationDate = dateFormatter(favoritePicDao.publicationDate),
            isFavorite = true
        )
    }

    private fun dateFormatter(milliseconds: Long): String {
        return SimpleDateFormat(FORMAT_PATTERN).format(Date(milliseconds)).toString()
    }

    companion object {
        private const val FORMAT_PATTERN = "dd.MM.yyyy"
    }
}
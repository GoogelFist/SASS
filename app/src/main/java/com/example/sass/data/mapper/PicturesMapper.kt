package com.example.sass.data.mapper

import com.example.sass.data.datasource.local.picture.models.FavoritePictureDao
import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.data.datasource.remote.picture.models.PictureDto
import com.example.sass.data.datasource.remote.picture.models.PicturesResponse
import com.example.sass.domain.models.FavoritePictureItem
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PicturesMapper @Inject constructor() {

    fun mapPicturesDaoToPicturesItems(
        picturesDao: List<PictureDao>,
        favoriteIds: List<String>
    ): List<PicturesItem> {
        val ids = favoriteIds.toHashSet()
        return picturesDao.map { mapPictureDaoToPictureItem(it, ids) }
    }

    fun mapPicturesResponseToPicturesDao(picturesResponse: PicturesResponse): List<PictureDao> {
        return picturesResponse.map { mapPictureDtoToPictureDao(it) }
    }

    fun mapFavoritePicturesDaoToFavoritePicturesItems(favoritePicsDao: List<FavoritePictureDao>): List<FavoritePictureItem> {
        return favoritePicsDao.map { mapFavoritePictureDaoToFavoritePictureItem(it) }
    }

    fun mapPictureDaoToFavoritePictureDao(pictureDao: PictureDao): FavoritePictureDao {
        return FavoritePictureDao(
            id = pictureDao.id,
            photoUrl = pictureDao.photoUrl,
            title = pictureDao.title,
            content = pictureDao.content,
            publicationDate = pictureDao.publicationDate,
            isFavorite = true,
            addedTimeMills = System.currentTimeMillis()
        )
    }

    fun mapPictureDaoToPictureDetail(pictureDao: PictureDao): PictureDetail {
        return PictureDetail(
            id = pictureDao.id,
            photoUrl = pictureDao.photoUrl,
            title = pictureDao.title,
            content = pictureDao.content,
            publicationDate = dateFormatter(pictureDao.publicationDate)
        )
    }

    private fun mapPictureDaoToPictureItem(
        pictureDao: PictureDao,
        favoriteIds: Set<String>
    ): PicturesItem {

        val isFavorite = favoriteIds.contains(pictureDao.id)

        return PicturesItem(
            id = pictureDao.id,
            photoUrl = pictureDao.photoUrl,
            title = pictureDao.title,
            isFavorite = isFavorite
        )
    }

    private fun mapPictureDtoToPictureDao(pictureDto: PictureDto): PictureDao {
        return PictureDao(
            content = pictureDto.content,
            id = pictureDto.id,
            photoUrl = pictureDto.photoUrl,
            publicationDate = pictureDto.publicationDate,
            title = pictureDto.title
        )
    }

    private fun mapFavoritePictureDaoToFavoritePictureItem(favoritePictureDao: FavoritePictureDao): FavoritePictureItem {
        return FavoritePictureItem(
            id = favoritePictureDao.id,
            photoUrl = favoritePictureDao.photoUrl,
            title = favoritePictureDao.title,
            content = favoritePictureDao.content,
            publicationDate = dateFormatter(favoritePictureDao.publicationDate),
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
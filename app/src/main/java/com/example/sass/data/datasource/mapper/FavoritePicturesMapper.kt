package com.example.sass.data.datasource.mapper

import com.example.sass.data.datasource.local.favoritepicture.models.FavoritePictureDao
import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.domain.models.FavoritePictureItem
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FavoritePicturesMapper @Inject constructor() {

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
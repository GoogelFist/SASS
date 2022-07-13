package com.example.sass.data.mapper

import com.example.sass.data.datasource.local.picture.models.PictureFavoriteDao
import com.example.sass.data.datasource.remote.picture.models.PictureDto
import com.example.sass.data.datasource.remote.picture.models.PicturesListResponse
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesMapper @Inject constructor() {

    fun mapPicturesListResponseToPicturesItem(
        picturesListResponse: PicturesListResponse,
        favorites: List<PictureFavoriteDao>
    ): List<PicturesItem> {
        return picturesListResponse.map { mapPictureItemDtoToPictureItem(it, favorites) }
    }

    private fun mapPictureItemDtoToPictureItem(
        pictureDto: PictureDto,
        favorites: List<PictureFavoriteDao>
    ): PicturesItem {

        val isFavorite = checkIsFavorite(favorites, pictureDto)

        return PicturesItem(pictureDto.id, pictureDto.photoUrl, pictureDto.title, isFavorite)
    }

    // TODO: will use set
    private fun checkIsFavorite(
        favorites: List<PictureFavoriteDao>,
        pictureDto: PictureDto
    ): Boolean {
        var isFavorite = false
        for (favorite in favorites) {
            if (favorite.favoriteId == pictureDto.id) {
                isFavorite = true
                break
            }
        }
        return isFavorite
    }
}
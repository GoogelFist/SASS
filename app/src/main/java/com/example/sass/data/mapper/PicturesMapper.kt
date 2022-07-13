package com.example.sass.data.mapper

import com.example.sass.data.datasource.local.picture.models.PictureFavoriteDao
import com.example.sass.data.datasource.remote.picture.models.PictureDto
import com.example.sass.data.datasource.remote.picture.models.PicturesListResponse
import com.example.sass.domain.models.FavoritePicturesItem
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesMapper @Inject constructor() {

    fun mapPicturesListResponseToPicturesItem(
        picturesListResponse: PicturesListResponse,
        favorites: List<PictureFavoriteDao>
    ): List<PicturesItem> {
        val favoritesIds = favorites.map { it.favoriteId }.toHashSet()

        return picturesListResponse.map { mapPictureItemDtoToPictureItem(it, favoritesIds) }
    }

    private fun mapPictureItemDtoToPictureItem(
        pictureDto: PictureDto,
        favorites: Set<String>
    ): PicturesItem {

        val isFavorite = favorites.contains(pictureDto.id)

        return PicturesItem(pictureDto.id, pictureDto.photoUrl, pictureDto.title, isFavorite)
    }

    // TODO: will use set
    fun mapPictureItemDtoToFavoritePictureItem(pictureDto: PictureDto): FavoritePicturesItem {
        return FavoritePicturesItem(
            id = pictureDto.id,
            photoUrl = pictureDto.photoUrl,
            title = pictureDto.title,
            content = pictureDto.content,
            publicationDate = pictureDto.publicationDate,
            isFavorite = true
        )
    }
}
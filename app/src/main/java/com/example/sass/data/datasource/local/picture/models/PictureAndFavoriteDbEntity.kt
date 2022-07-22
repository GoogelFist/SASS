package com.example.sass.data.datasource.local.picture.models

import androidx.room.*
import com.example.sass.data.datasource.DataSourceStringUtils
import com.example.sass.domain.models.FavoritePictureItem
import com.example.sass.domain.models.PicturesItem

data class PictureAndFavoriteDbEntity(
    @Embedded val pictureDbEntity: PictureDbEntity,

    @Relation(
        parentColumn = "picture_id",
        entityColumn = "favorite_id"
    )
    val favoriteDbEntity: FavoriteDbEntity?
) {

    fun toPictureItem(): PicturesItem {
        return PicturesItem(
            id = pictureDbEntity.id,
            photoUrl = pictureDbEntity.photoUrl,
            title = pictureDbEntity.title,
            isFavorite = favoriteDbEntity != null
        )
    }

    fun toFavoritePictureItem(): FavoritePictureItem {
        return FavoritePictureItem(
            id = pictureDbEntity.id,
            photoUrl = pictureDbEntity.photoUrl,
            title = pictureDbEntity.title,
            content = pictureDbEntity.content,
            publicationDate = DataSourceStringUtils.dateFormatter(pictureDbEntity.publicationDate),
            isFavorite = favoriteDbEntity != null
        )
    }
}


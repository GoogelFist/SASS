package com.example.sass.data.datasource.local.picture.models

import androidx.room.*
import com.example.sass.data.datasource.StringUtils
import com.example.sass.domain.models.FavoritePictureItem
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.models.PicturesItem
import java.text.SimpleDateFormat
import java.util.*

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
            isFavorite = favoriteDbEntity?.isFavorite ?: false
        )
    }

    fun toFavoritePictureItem(): FavoritePictureItem {
        return FavoritePictureItem(
            id = pictureDbEntity.id,
            photoUrl = pictureDbEntity.photoUrl,
            title = pictureDbEntity.title,
            content = pictureDbEntity.content,
            publicationDate = StringUtils.dateFormatter(pictureDbEntity.publicationDate),
            isFavorite = favoriteDbEntity?.isFavorite ?: false
        )
    }
}


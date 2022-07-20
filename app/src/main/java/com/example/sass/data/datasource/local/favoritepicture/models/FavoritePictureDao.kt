package com.example.sass.data.datasource.local.favoritepicture.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pictures")
data class FavoritePictureDao(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",
    @ColumnInfo(name = "photo_url")
    val photoUrl: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "content")
    val content: String = "",
    @ColumnInfo(name = "publication_date")
    val publicationDate: Long = DEFAULT_PUBLICATION_DATE,
    @ColumnInfo(name = "added_time_mills")
    val addedTimeMills: Long = 0,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)

private const val DEFAULT_PUBLICATION_DATE = 0L
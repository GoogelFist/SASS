package com.example.sass.data.datasource.local.picture.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pictures_favorites")
data class PictureFavoriteDao(
    @PrimaryKey
    @ColumnInfo(name = "favorite_id")
    val favoriteId: String = "",
    @ColumnInfo(name = "added_time_mills")
    val addedTimeMills: Long = 0
)
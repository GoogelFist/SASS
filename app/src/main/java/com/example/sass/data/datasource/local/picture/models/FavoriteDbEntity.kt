package com.example.sass.data.datasource.local.picture.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "favorite_id")
    val pictureId: String = "",
    @ColumnInfo(name = "added_date")
    val addedDate: Long = DEFAULT_ADDED_DATE
)

private const val DEFAULT_ADDED_DATE = 0L
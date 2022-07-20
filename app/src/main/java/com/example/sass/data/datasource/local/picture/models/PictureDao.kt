package com.example.sass.data.datasource.local.picture.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pictures")
data class PictureDao(
    @ColumnInfo(name = "content")
    val content: String = "",
    @PrimaryKey
    @ColumnInfo(name = "picture_id")
    val id: String = "",
    @ColumnInfo(name = "photo_url")
    val photoUrl: String = "",
    @ColumnInfo(name = "publication_date")
    val publicationDate: Long = DEFAULT_PUBLICATION_DATE,
    @ColumnInfo(name = "title")
    val title: String = ""
)

private const val DEFAULT_PUBLICATION_DATE = 0L
package com.example.sass.data.datasource.local.picture.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sass.data.datasource.StringUtils
import com.example.sass.data.datasource.remote.picture.models.PictureDto
import com.example.sass.domain.models.PictureDetail
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "pictures")
data class PictureDbEntity(
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
) {
    fun toPictureDetail(): PictureDetail {
        return PictureDetail(
            id = id,
            photoUrl = photoUrl,
            title = title,
            content = content,
            publicationDate = StringUtils.dateFormatter(publicationDate)
        )
    }

    companion object {
        private const val DEFAULT_PUBLICATION_DATE = 0L

        fun fromPictureDto(pictureDto: PictureDto): PictureDbEntity {
            return PictureDbEntity(
                content = pictureDto.content,
                id = pictureDto.id,
                photoUrl = pictureDto.photoUrl,
                publicationDate = pictureDto.publicationDate,
                title = pictureDto.title
            )
        }
    }
}
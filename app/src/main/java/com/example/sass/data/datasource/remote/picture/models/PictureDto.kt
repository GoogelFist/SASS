package com.example.sass.data.datasource.remote.picture.models


import com.google.gson.annotations.SerializedName

data class PictureDto(
    @SerializedName("content")
    val content: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("photoUrl")
    val photoUrl: String = "",
    @SerializedName("publicationDate")
    val publicationDate: Long = DEFAULT_PUBLICATION_DATE,
    @SerializedName("title")
    val title: String = ""
)

private const val DEFAULT_PUBLICATION_DATE = 0L
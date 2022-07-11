package com.example.sass.data.mapper

import com.example.sass.data.datasource.remote.picture.models.PictureDto
import com.example.sass.data.datasource.remote.picture.models.PicturesListResponse
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesMapper @Inject constructor() {

    fun mapPicturesListResponseToPicturesItem(picturesListResponse: PicturesListResponse): List<PicturesItem> {
        return picturesListResponse.map { mapPictureItemDtoToPictureItem(it) }
    }

    private fun mapPictureItemDtoToPictureItem(pictureDto: PictureDto): PicturesItem {
        return PicturesItem(pictureDto.id, pictureDto.photoUrl, pictureDto.title)
    }
}
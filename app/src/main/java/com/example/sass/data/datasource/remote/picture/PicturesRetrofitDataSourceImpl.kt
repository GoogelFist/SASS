package com.example.sass.data.datasource.remote.picture

import com.example.sass.data.IncorrectTokenException
import com.example.sass.data.datasource.local.picture.models.PictureFavoriteDao
import com.example.sass.data.datasource.remote.picture.models.PictureDto
import com.example.sass.data.datasource.remote.picture.models.PicturesListResponse
import com.example.sass.data.mapper.PicturesMapper
import com.example.sass.domain.models.FavoritePicturesItem
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRetrofitDataSourceImpl @Inject constructor(
    private val picturesRetrofitService: PicturesRetrofitService,
    private val mapper: PicturesMapper
) :
    PicturesRemoteDataSource {

    private lateinit var picturesResponse: PicturesListResponse

    override suspend fun loadPictures(token: String) {

        if (token.isBlank()) {
            throw IncorrectTokenException(ABSENT_TOKEN_MESSAGE)
        } else {
            val response = picturesRetrofitService.loadPictures(token)
            if (response.isSuccessful) {
                response.body()?.let { listResponse ->
                    picturesResponse = listResponse
                } ?: throw throw RuntimeException(REQUEST_BODY_NULL_MESSAGE)
            } else {
                val code = response.code()
                if (code == INCORRECT_TOKEN_CODE_RESPONSE) {
                    throw IncorrectTokenException("${response.code()} : ${response.message()}")
                }
                throw RuntimeException("${response.code()} : ${response.message()}")
            }
        }
    }

    override suspend fun getPicturesItems(favorites: List<PictureFavoriteDao>): List<PicturesItem> {
        return mapper.mapPicturesListResponseToPicturesItem(picturesResponse, favorites)
    }

    // TODO:
    override suspend fun getFavoritePicturesItems(favorites: List<PictureFavoriteDao>): List<FavoritePicturesItem> {

        return favorites
            .map { favorite ->
                picturesResponse.find { pictureDto -> favorite.favoriteId == pictureDto.id }
                    ?: PictureDto()
            }
            .map { mapper.mapPictureItemDtoToFavoritePictureItem(it) }
    }

    override suspend fun getFavoritePictureById(id: String): FavoritePicturesItem {
        val pictureDto = picturesResponse.find { it.id == id } ?: PictureDto()
        return mapper.mapPictureItemDtoToFavoritePictureItem(pictureDto)
    }


    companion object {
        private const val ABSENT_TOKEN_MESSAGE = "Token is absent"
        private const val REQUEST_BODY_NULL_MESSAGE = "Request body is null"
        private const val INCORRECT_TOKEN_CODE_RESPONSE = 401
    }
}
package com.example.sass.data.datasource.remote.picture

import com.example.sass.data.IncorrectTokenException
import com.example.sass.data.mapper.PicturesMapper
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRetrofitDataSourceImpl @Inject constructor(
    private val picturesRetrofitService: PicturesRetrofitService,
    private val mapper: PicturesMapper
) :
    PicturesRemoteDataSource {
    override suspend fun loadPictures(token: String): List<PicturesItem> {

        if (token.isBlank()) {
            throw IncorrectTokenException(ABSENT_TOKEN_MESSAGE)
        } else {
            val response = picturesRetrofitService.loadPictures(token)
            if (response.isSuccessful) {
                response.body()?.let { listResponse ->
                    return mapper.mapPicturesListResponseToPicturesItem(listResponse)
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

    companion object {
        private const val ABSENT_TOKEN_MESSAGE = "Token is absent"
        private const val REQUEST_BODY_NULL_MESSAGE = "Request body is null"
        private const val INCORRECT_TOKEN_CODE_RESPONSE = 401
    }
}
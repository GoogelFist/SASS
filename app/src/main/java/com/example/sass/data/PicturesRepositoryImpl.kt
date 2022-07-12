package com.example.sass.data

import com.example.sass.data.datasource.local.token.TokenLocalDataSource
import com.example.sass.data.datasource.local.user.UserLocalDataSource
import com.example.sass.data.datasource.remote.picture.PicturesRemoteDataSource
import com.example.sass.data.mapper.PicturesMapper
import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.models.PicturesItem
import javax.inject.Inject

class PicturesRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val picturesRemoteDataSource: PicturesRemoteDataSource,
    private val mapper: PicturesMapper
) : PicturesRepository {

    override suspend fun loadPictures(): List<PicturesItem> {

        val token = tokenLocalDataSource.loadAuthToken()

        if (token.isBlank()) {
            userLocalDataSource.deleteUserInfo()
            throw RuntimeException(ABSENT_TOKEN_MESSAGE)

        } else {
            val response = picturesRemoteDataSource.loadPictures(token)

            if (response.isSuccessful) {
                response.body()?.let { listResponse ->
                    return mapper.mapPicturesListResponseToPicturesItem(listResponse)
                }
                throw throw RuntimeException(REQUEST_BODY_NULL_MESSAGE)
            } else {
                val code = response.code()
                if (code == INCORRECT_TOKEN_CODE_RESPONSE) {
                    clearUserData()
                    throw IncorrectTokenException("${response.code()} : ${response.message()}")
                }
                throw RuntimeException("${response.code()} : ${response.message()}")
            }
        }
    }

    private suspend fun clearUserData() {
        tokenLocalDataSource.deleteAuthToken()
        userLocalDataSource.deleteUserInfo()
    }

    companion object {
        private const val ABSENT_TOKEN_MESSAGE = "Token is absent"
        private const val REQUEST_BODY_NULL_MESSAGE = "Request body is null"
        private const val INCORRECT_TOKEN_CODE_RESPONSE = 401
    }
}
package com.example.sass.data.datasource.remote.user

import com.example.sass.data.IncorrectTokenException
import com.example.sass.data.datasource.remote.user.models.SignInDto
import com.example.sass.data.datasource.remote.user.models.SignInRequest
import com.example.sass.data.datasource.mapper.UserMapper
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(
    private val userRetrofitService: UserRetrofitService,
    private val userMapper: UserMapper
) : UserRemoteDataSource {

    override suspend fun signIn(phone: String, password: String): SignInDto {

        val response = userRetrofitService.signIn(SignInRequest(phone, password))

        if (response.isSuccessful) {
            response.body()?.let { body ->
                val userInfoDao = userMapper.mapUserInfoDtoToUserInfoDao(body.userInfoDTO)
                return SignInDto(body.token, userInfoDao)
            } ?: throw throw RuntimeException(REQUEST_BODY_NULL_MESSAGE)
        } else {
            throw RuntimeException("${response.code()} : ${response.message()}")
        }
    }

    override suspend fun signOut(token: String) {

        if (token.isBlank()) {
            throw IncorrectTokenException(ABSENT_TOKEN_MESSAGE)
        } else {
            val response = userRetrofitService.signOut(token)

            if (!response.isSuccessful) {
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
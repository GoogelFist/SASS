package com.example.sass.data.user

import com.example.sass.data.user.datasource.local.UserLocalDataSource
import com.example.sass.data.user.datasource.remote.UserRemoteDataSource
import com.example.sass.data.user.datasource.remote.models.SignInRequest
import com.example.sass.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userMapper: UserMapper
) : UserRepository {
    override suspend fun signIn(phone: String, password: String) {
        val response = userRemoteDataSource.signIn(SignInRequest(phone, password))

        if (response.isSuccessful) {
            response.body()?.let { body ->
                val userInfoDao = userMapper.mapUserInfoDtoToUserInfoDao(body.userInfoDTO)
                userLocalDataSource.saveUserInfo(userInfoDao)
                userLocalDataSource.saveAuthToken(body.token)
            }
        } else {
            throw RuntimeException("${response.code()} : ${response.message()}")
        }
    }

    override suspend fun isAbsentToken(): Boolean {
        return userLocalDataSource.isAbsentToken()
    }

    override suspend fun signOut() {

        val token = loadAuthToken()

        if (token.isBlank()) {
            clearUserData()
        } else {
            val formatToken = formatToken(token)
            val response = userRemoteDataSource.signOut(formatToken)

            if (response.isSuccessful) {
                clearUserData()
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
        userLocalDataSource.deleteAuthToken()
        userLocalDataSource.deleteUserInfo()
    }

    override suspend fun loadAuthToken(): String {
        return userLocalDataSource.loadAuthToken()
    }


    private fun formatToken(token: String): String {
        return "Token $token"
    }

    companion object {
        private const val INCORRECT_TOKEN_CODE_RESPONSE = 401
    }
}

class IncorrectTokenException(message: String) : Throwable(message)
package com.example.sass.data

import com.example.sass.data.datasource.local.token.TokenLocalDataSource
import com.example.sass.data.datasource.local.user.UserLocalDataSource
import com.example.sass.data.datasource.remote.user.UserRemoteDataSource
import com.example.sass.data.datasource.remote.user.models.SignInRequest
import com.example.sass.data.mapper.UserMapper
import com.example.sass.domain.UserRepository
import com.example.sass.domain.models.UserInfo
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val userMapper: UserMapper
) : UserRepository {
    override suspend fun signIn(phone: String, password: String) {

        AuthDataUtils.validateSignInData(phone, password)
        val formatted = AuthDataUtils.formatPhone(phone)

        val response = userRemoteDataSource.signIn(SignInRequest(formatted, password))

        if (response.isSuccessful) {
            response.body()?.let { body ->
                val userInfoDao = userMapper.mapUserInfoDtoToUserInfoDao(body.userInfoDTO)
                userLocalDataSource.saveUserInfo(userInfoDao)
                tokenLocalDataSource.saveAuthToken(body.token)
            }
        } else {
            throw RuntimeException("${response.code()} : ${response.message()}")
        }
    }

    override suspend fun isAbsentToken(): Boolean {
        return tokenLocalDataSource.isAbsentToken()
    }

    override suspend fun signOut() {

        val token = loadAuthToken()

        if (token.isBlank()) {
            userLocalDataSource.deleteUserInfo()
        } else {
            val response = userRemoteDataSource.signOut(token)

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

    override suspend fun loadUserInfo(): UserInfo {
        val userInfoDao = userLocalDataSource.loadUserInfo()
        return userMapper.mapUserInfoDaoToUserInfo(userInfoDao)
    }

    private suspend fun loadAuthToken(): String {
        return tokenLocalDataSource.loadAuthToken()
    }

    private suspend fun clearUserData() {
        tokenLocalDataSource.deleteAuthToken()
        userLocalDataSource.deleteUserInfo()
    }

    companion object {
        private const val INCORRECT_TOKEN_CODE_RESPONSE = 401
    }
}
package com.example.sass.data.user

import com.example.sass.data.user.datasource.local.UserLocalDataSource
import com.example.sass.data.user.datasource.remote.UserRemoteDataSource
import com.example.sass.data.user.datasource.remote.models.SignInRequest
import com.example.sass.domain.UserRepository
import javax.inject.Inject

class UserUserRepository @Inject constructor(
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
}
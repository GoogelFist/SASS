package com.example.sass.data

import com.example.sass.data.datasource.local.LocalDataSource
import com.example.sass.data.datasource.remote.RemoteDataSource
import com.example.sass.data.datasource.remote.models.SignInRequest
import com.example.sass.domain.Repository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val mapper: Mapper
) : Repository {
    override suspend fun signIn(phone: String, password: String) {
        val response = remoteDataSource.signIn(SignInRequest(phone, password))

        if (response.isSuccessful) {
            response.body()?.let { body ->
                val userInfoDao = mapper.mapUserInfoDtoToUserInfoDao(body.userInfoDTO)
                localDataSource.saveUserInfo(userInfoDao)
                localDataSource.saveAuthToken(body.token)
            }
        } else {
            throw RuntimeException("${response.code()} : ${response.message()}")
        }
    }
}
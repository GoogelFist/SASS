package com.example.sass.data

import android.util.Log
import com.example.sass.data.datasourse.remote.RemoteDataSource
import com.example.sass.data.datasourse.remote.models.SignInRequest
import com.example.sass.domain.Repository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val mapper: Mapper
) : Repository {
    override suspend fun signIn(phone: String, password: String) {
        val response = remoteDataSource.signIn(SignInRequest(phone, password))

        if (response.isSuccessful) {
            response.body()?.let { body ->
                val userInfoDao = mapper.mapUserInfoDtoToUserInfoDao(body.userInfoDTO)
                Log.e(this.toString(), userInfoDao.toString())
            }
        } else {
            Log.e(this.toString(), "${response.code()} : ${response.message()}")
            throw RuntimeException("${response.code()} : ${response.message()}")
        }
    }
}
package com.example.sass.data.user.datasource.remote

import com.example.sass.data.user.datasource.remote.models.SignInRequest
import com.example.sass.data.user.datasource.remote.models.SignInResponse
import retrofit2.Response
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(private val userRetrofitService: UserRetrofitService) :
    UserRemoteDataSource {
    override suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponse> {
        return userRetrofitService.signIn(signInRequest)
    }
}
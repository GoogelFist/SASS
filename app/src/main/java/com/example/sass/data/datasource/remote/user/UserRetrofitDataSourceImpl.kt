package com.example.sass.data.datasource.remote.user

import com.example.sass.data.datasource.remote.user.models.SignInRequest
import com.example.sass.data.datasource.remote.user.models.SignInResponse
import com.example.sass.data.datasource.remote.user.models.SignOutResponse
import retrofit2.Response
import javax.inject.Inject

class UserRetrofitDataSourceImpl @Inject constructor(private val userRetrofitService: UserRetrofitService) :
    UserRemoteDataSource {
    override suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponse> {
        return userRetrofitService.signIn(signInRequest)
    }

    override suspend fun signOut(token: String): Response<SignOutResponse> {
        return userRetrofitService.signOut(token)
    }
}
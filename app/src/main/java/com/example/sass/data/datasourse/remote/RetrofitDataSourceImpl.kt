package com.example.sass.data.datasourse.remote

import com.example.sass.data.datasourse.remote.models.SignInRequest
import com.example.sass.data.datasourse.remote.models.SignInResponse
import retrofit2.Response
import javax.inject.Inject

class RetrofitDataSourceImpl @Inject constructor(private val retrofitService: RetrofitService) :
    RemoteDataSource {
    override suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponse> {
        return retrofitService.signIn(signInRequest)
    }
}
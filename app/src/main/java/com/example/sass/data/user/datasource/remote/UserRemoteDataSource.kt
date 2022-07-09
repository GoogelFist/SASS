package com.example.sass.data.user.datasource.remote

import com.example.sass.data.user.datasource.remote.models.SignInRequest
import com.example.sass.data.user.datasource.remote.models.SignInResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponse>
}
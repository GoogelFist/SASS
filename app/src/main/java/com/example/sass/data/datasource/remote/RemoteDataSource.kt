package com.example.sass.data.datasource.remote

import com.example.sass.data.datasource.remote.models.SignInRequest
import com.example.sass.data.datasource.remote.models.SignInResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponse>
}
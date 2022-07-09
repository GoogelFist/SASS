package com.example.sass.data.user.datasource.remote

import com.example.sass.data.user.datasource.remote.models.SignInRequest
import com.example.sass.data.user.datasource.remote.models.SignInResponse
import com.example.sass.data.user.datasource.remote.models.SignOutResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponse>

    suspend fun signOut(token: String): Response<SignOutResponse>
}
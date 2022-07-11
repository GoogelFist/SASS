package com.example.sass.data.datasource.remote.user

import com.example.sass.data.datasource.remote.user.models.SignInRequest
import com.example.sass.data.datasource.remote.user.models.SignInResponse
import com.example.sass.data.datasource.remote.user.models.SignOutResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponse>

    suspend fun signOut(token: String): Response<SignOutResponse>
}
package com.example.sass.data.datasourse.remote

import com.example.sass.data.datasourse.remote.models.SignInRequest
import com.example.sass.data.datasourse.remote.models.SignInResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponse>
}
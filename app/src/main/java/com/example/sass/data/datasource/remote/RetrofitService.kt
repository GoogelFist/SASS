package com.example.sass.data.datasource.remote

import com.example.sass.data.datasource.remote.models.SignInRequest
import com.example.sass.data.datasource.remote.models.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("auth/login")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>
}
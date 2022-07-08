package com.example.sass.data.datasourse.remote

import com.example.sass.data.datasourse.remote.models.SignInRequest
import com.example.sass.data.datasourse.remote.models.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

    @POST("auth/login")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>
}
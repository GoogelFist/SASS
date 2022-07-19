package com.example.sass.data.datasource.remote.user

import com.example.sass.data.datasource.remote.user.models.SignInRequest
import com.example.sass.data.datasource.remote.user.models.SignInResponse
import com.example.sass.data.datasource.remote.user.models.SignOutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserRetrofitService {

    @POST("auth/login")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @POST("auth/logout")
    @Headers("accept: */*")
    suspend fun signOut(@Header("Authorization") token: String): Response<SignOutResponse>
}
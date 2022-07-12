package com.example.sass.data.datasource.remote.user

import com.example.sass.data.datasource.remote.user.models.SignInDto

interface UserRemoteDataSource {
    suspend fun signIn(phone: String, password: String): SignInDto

    suspend fun signOut(token: String)
}
package com.example.sass.domain

import com.example.sass.domain.models.UserInfo

interface UserRepository {
    suspend fun signIn(phone: String, password: String)

    suspend fun isAbsentToken(): Boolean

    suspend fun signOut()

    suspend fun loadUserInfo(): UserInfo

    suspend fun clearUserData()
}
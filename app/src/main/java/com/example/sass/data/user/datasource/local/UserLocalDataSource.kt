package com.example.sass.data.user.datasource.local

import com.example.sass.data.user.datasource.local.models.UserInfoDao

interface UserLocalDataSource {

    suspend fun saveUserInfo(userInfoDao: UserInfoDao)

    suspend fun loadUserInfo(): UserInfoDao

    suspend fun deleteUserInfo()

    suspend fun saveAuthToken(token: String)

    suspend fun loadAuthToken(): String

    suspend fun deleteAuthToken()

    suspend fun isAbsentToken(): Boolean
}
package com.example.sass.data.datasource.local

import com.example.sass.data.datasource.local.models.UserInfoDao

interface LocalDataSource {

    suspend fun saveUserInfo(userInfoDao: UserInfoDao)

    suspend fun loadUserInfo(): UserInfoDao

    suspend fun saveAuthToken(token: String)

    suspend fun loadAuthToken(): String
}
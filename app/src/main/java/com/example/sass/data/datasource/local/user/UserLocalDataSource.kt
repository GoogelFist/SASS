package com.example.sass.data.datasource.local.user

import com.example.sass.data.datasource.local.user.models.UserInfoDao

interface UserLocalDataSource {

    suspend fun saveUserInfo(userInfoDao: UserInfoDao)

    suspend fun loadUserInfo(): UserInfoDao

    suspend fun deleteUserInfo()
}
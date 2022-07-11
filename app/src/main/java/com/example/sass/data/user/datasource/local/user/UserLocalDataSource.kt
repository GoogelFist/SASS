package com.example.sass.data.user.datasource.local.user

import com.example.sass.data.user.datasource.local.models.UserInfoDao

interface UserLocalDataSource {

    suspend fun saveUserInfo(userInfoDao: UserInfoDao)

    suspend fun loadUserInfo(): UserInfoDao

    suspend fun deleteUserInfo()
}
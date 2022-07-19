package com.example.sass.data.datasource.local.user

import com.example.sass.data.datasource.local.user.models.UserInfoDao
import com.example.sass.domain.models.UserInfo

interface UserLocalDataSource {

    suspend fun saveUserInfo(userInfoDao: UserInfoDao)

    suspend fun loadUserInfo(): UserInfo

    suspend fun deleteUserInfo()
}
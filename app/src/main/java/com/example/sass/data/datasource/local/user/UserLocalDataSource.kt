package com.example.sass.data.datasource.local.user

import com.example.sass.data.datasource.local.user.models.UserInfoDbEntity
import com.example.sass.domain.models.UserInfo

interface UserLocalDataSource {

    suspend fun saveUserInfo(userInfoDbEntity: UserInfoDbEntity)

    suspend fun loadUserInfo(): UserInfo

    suspend fun deleteUserInfo()
}
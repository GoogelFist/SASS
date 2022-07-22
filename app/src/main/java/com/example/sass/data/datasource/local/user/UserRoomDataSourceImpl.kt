package com.example.sass.data.datasource.local.user

import com.example.sass.data.datasource.local.user.models.UserInfoDbEntity
import com.example.sass.domain.models.UserInfo
import javax.inject.Inject

class UserRoomDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {

    override suspend fun saveUserInfo(userInfoDbEntity: UserInfoDbEntity) {
        userDao.saveUserInfo(userInfoDbEntity)
    }

    override suspend fun loadUserInfo(): UserInfo {
        userDao.loadUserInfo(USER_DB_ID)?.let { userInfoDbEntity ->
            return userInfoDbEntity.toUserInfo()
        }
        return UserInfo()
    }

    override suspend fun deleteUserInfo() {
        userDao.deleteUserInfo(USER_DB_ID)
    }

    companion object {
        private const val USER_DB_ID = 1
    }
}
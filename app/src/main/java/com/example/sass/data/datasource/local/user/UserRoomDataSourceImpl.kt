package com.example.sass.data.datasource.local.user

import com.example.sass.data.datasource.local.user.models.UserInfoDao
import com.example.sass.data.mapper.UserMapper
import com.example.sass.domain.models.UserInfo
import javax.inject.Inject

class UserRoomDataSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val mapper: UserMapper
) : UserLocalDataSource {

    override suspend fun saveUserInfo(userInfoDao: UserInfoDao) {
        userDao.saveUserInfo(userInfoDao)
    }

    override suspend fun loadUserInfo(): UserInfo {
        val userDao = userDao.loadUserInfo(USER_DB_ID)
        userDao?.let { userInfoDao ->
            return mapper.mapUserInfoDaoToUserInfo(userInfoDao)
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
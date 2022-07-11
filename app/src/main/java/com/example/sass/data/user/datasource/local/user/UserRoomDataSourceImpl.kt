package com.example.sass.data.user.datasource.local.user

import com.example.sass.data.user.datasource.local.models.UserInfoDao
import javax.inject.Inject

class UserRoomDataSourceImpl @Inject constructor(private val userDao: UserDao) :
    UserLocalDataSource {

    override suspend fun saveUserInfo(userInfoDao: UserInfoDao) {
        userDao.saveUserInfo(userInfoDao)
    }

    override suspend fun loadUserInfo(): UserInfoDao {
        val userDao = userDao.loadUserInfo(USER_DB_ID)
        userDao?.let {
            return it
        }
        return UserInfoDao()
    }

    override suspend fun deleteUserInfo() {
        userDao.deleteUserInfo(USER_DB_ID)
    }

    companion object {
        private const val USER_DB_ID = 1
    }
}
package com.example.sass.data.user.datasource.local

import com.example.sass.data.user.datasource.local.models.AuthTokenDao
import com.example.sass.data.user.datasource.local.models.UserInfoDao
import javax.inject.Inject

class UserRoomDataSourceImpl @Inject constructor(private val userDao: UserDao) : UserLocalDataSource {

    override suspend fun saveUserInfo(userInfoDao: UserInfoDao) {
        userDao.saveUserInfo(userInfoDao)
    }

    override suspend fun loadUserInfo(): UserInfoDao {
        val userDao = userDao.loadUserInfo()
        userDao?.let {
            return it
        }
        return UserInfoDao()
    }

    override suspend fun saveAuthToken(token: String) {
        userDao.saveAuthToken(AuthTokenDao(TOKEN_ID, token))
    }

    override suspend fun loadAuthToken(): String {
        val tokenDao = userDao.loadAuthToken(TOKEN_ID)
        tokenDao?.let {
            return it.token
        }
        return ABSENT_TOKEN
    }

    companion object {
        private const val TOKEN_ID = 1

        private const val ABSENT_TOKEN = ""
    }
}
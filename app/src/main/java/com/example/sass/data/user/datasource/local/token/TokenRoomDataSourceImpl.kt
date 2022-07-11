package com.example.sass.data.user.datasource.local.token

import com.example.sass.data.user.datasource.local.models.AuthTokenDao
import javax.inject.Inject

class TokenRoomDataSourceImpl @Inject constructor(private val tokenDao: TokenDao) :
    TokenLocalDataSource {

    override suspend fun saveAuthToken(token: String) {
        tokenDao.saveAuthToken(AuthTokenDao(TOKEN_ID, token))
    }

    override suspend fun loadAuthToken(): String {
        val tokenDao = tokenDao.loadAuthToken(TOKEN_ID)
        tokenDao?.let {
            return it.token
        }
        return ABSENT_TOKEN
    }

    override suspend fun deleteAuthToken() {
        tokenDao.deleteAuthToken(TOKEN_ID)
    }

    override suspend fun isAbsentToken(): Boolean {
        val token = loadAuthToken()
        return token.isBlank()
    }

    companion object {
        private const val TOKEN_ID = 1

        private const val ABSENT_TOKEN = ""
    }
}
package com.example.sass.data.datasource.local.token

import com.example.sass.data.datasource.local.token.models.AuthTokenDao
import javax.inject.Inject

class TokenRoomDataSourceImpl @Inject constructor(private val tokenDao: TokenDao) :
    TokenLocalDataSource {

    override suspend fun saveAuthToken(token: String) {
        tokenDao.saveAuthToken(AuthTokenDao(TOKEN_ID, formatToken(token)))
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

    override suspend fun isExistToken(): Boolean {
        val token = loadAuthToken()
        return token.isNotBlank()
    }

    private fun formatToken(token: String): String {
        return "Token $token"
    }

    companion object {
        private const val TOKEN_ID = 1

        private const val ABSENT_TOKEN = ""
    }
}
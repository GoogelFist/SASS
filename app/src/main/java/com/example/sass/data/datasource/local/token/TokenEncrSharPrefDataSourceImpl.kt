package com.example.sass.data.datasource.local.token

import android.content.SharedPreferences
import javax.inject.Inject


class TokenEncrSharPrefDataSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    TokenLocalDataSource {

    override suspend fun saveAuthToken(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN_KEY, formatToken(token))
            .apply()
    }

    override suspend fun loadAuthToken(): String {
        return sharedPreferences.getString(TOKEN_KEY, DEFAULT_TOKEN_VALUE) ?: DEFAULT_TOKEN_VALUE
    }

    override suspend fun deleteAuthToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }

    override suspend fun isExistToken(): Boolean {
        val token = loadAuthToken()
        return token.isNotBlank()
    }

    private fun formatToken(token: String): String {
        return "Token $token"
    }

    companion object {
        private const val TOKEN_KEY = "TOKEN"
        private const val DEFAULT_TOKEN_VALUE = ""
    }
}
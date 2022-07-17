package com.example.sass.data.datasource.local.token

interface TokenLocalDataSource {

    suspend fun saveAuthToken(token: String)

    suspend fun loadAuthToken(): String

    suspend fun deleteAuthToken()

    suspend fun isExistToken(): Boolean
}
package com.example.sass.data.user.datasource.local.token

interface TokenLocalDataSource {

    suspend fun saveAuthToken(token: String)

    suspend fun loadAuthToken(): String

    suspend fun deleteAuthToken()

    suspend fun isAbsentToken(): Boolean
}
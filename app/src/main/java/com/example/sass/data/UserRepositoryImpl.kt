package com.example.sass.data

import com.example.sass.data.datasource.local.token.TokenLocalDataSource
import com.example.sass.data.datasource.local.user.UserLocalDataSource
import com.example.sass.data.datasource.remote.user.UserRemoteDataSource
import com.example.sass.domain.UserRepository
import com.example.sass.domain.models.UserInfo
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
) : UserRepository {

    override suspend fun signIn(phone: String, password: String) {
        val signInDto = userRemoteDataSource.signIn(phone, password)

        userLocalDataSource.saveUserInfo(signInDto.userInfoDbEntity)
        tokenLocalDataSource.saveAuthToken(signInDto.token)
    }

    override suspend fun isSignedIn(): Boolean {
        return tokenLocalDataSource.isExistToken()
    }

    override suspend fun signOut() {
        val token = loadAuthToken()
        userRemoteDataSource.signOut(token)
    }

    override suspend fun loadUserInfo(): UserInfo {
        return userLocalDataSource.loadUserInfo()
    }

    override suspend fun clearData() {
        tokenLocalDataSource.deleteAuthToken()
        userLocalDataSource.deleteUserInfo()
    }

    private suspend fun loadAuthToken(): String {
        return tokenLocalDataSource.loadAuthToken()
    }
}
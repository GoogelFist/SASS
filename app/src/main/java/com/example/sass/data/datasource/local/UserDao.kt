package com.example.sass.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.datasource.local.models.AuthTokenDao
import com.example.sass.data.datasource.local.models.UserInfoDao

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserInfo(userInfoDao: UserInfoDao)

    @Query("SELECT * FROM user_info")
    suspend fun loadUserInfo(): UserInfoDao?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAuthToken(tokenDao: AuthTokenDao)

    @Query("SELECT * FROM user_token WHERE id = :id")
    suspend fun loadAuthToken(id: Int): AuthTokenDao?
}
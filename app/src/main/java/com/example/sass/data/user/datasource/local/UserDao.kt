package com.example.sass.data.user.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.user.datasource.local.models.AuthTokenDao
import com.example.sass.data.user.datasource.local.models.UserInfoDao

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserInfo(userInfoDao: UserInfoDao)

    @Query("SELECT * FROM user_info WHERE id = :id")
    suspend fun loadUserInfo(id: Int): UserInfoDao?

    @Query("DELETE FROM user_info WHERE prime_id = :id")
    suspend fun deleteUserInfo(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAuthToken(tokenDao: AuthTokenDao)

    @Query("SELECT * FROM user_token WHERE id = :id")
    suspend fun loadAuthToken(id: Int): AuthTokenDao?

    @Query("DELETE FROM user_token WHERE id = :id")
    suspend fun deleteAuthToken(id: Int)
}
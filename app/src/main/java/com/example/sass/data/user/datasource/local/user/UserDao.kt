package com.example.sass.data.user.datasource.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.user.datasource.local.models.UserInfoDao

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserInfo(userInfoDao: UserInfoDao)

    @Query("SELECT * FROM user_info WHERE prime_id = :id")
    suspend fun loadUserInfo(id: Int): UserInfoDao?

    @Query("DELETE FROM user_info WHERE prime_id = :id")
    suspend fun deleteUserInfo(id: Int)
}
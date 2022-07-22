package com.example.sass.data.datasource.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.datasource.local.user.models.UserInfoDbEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserInfo(userInfoDbEntity: UserInfoDbEntity)

    @Query("SELECT * FROM user_info WHERE prime_id = :id")
    suspend fun loadUserInfo(id: Int): UserInfoDbEntity?

    @Query("DELETE FROM user_info WHERE prime_id = :id")
    suspend fun deleteUserInfo(id: Int)
}
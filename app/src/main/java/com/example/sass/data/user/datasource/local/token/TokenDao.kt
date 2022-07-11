package com.example.sass.data.user.datasource.local.token

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sass.data.user.datasource.local.models.AuthTokenDao

@Dao
interface TokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAuthToken(tokenDao: AuthTokenDao)

    @Query("SELECT * FROM user_token WHERE id = :id")
    suspend fun loadAuthToken(id: Int): AuthTokenDao?

    @Query("DELETE FROM user_token WHERE id = :id")
    suspend fun deleteAuthToken(id: Int)
}
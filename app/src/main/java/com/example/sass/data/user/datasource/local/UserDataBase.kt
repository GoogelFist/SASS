package com.example.sass.data.user.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sass.data.user.datasource.local.models.AuthTokenDao
import com.example.sass.data.user.datasource.local.models.UserInfoDao

@Database(
    entities = [AuthTokenDao::class, UserInfoDao::class],
    version = DB_VERSION,
    exportSchema = false
)

abstract class UserDataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}

private const val DB_VERSION = 1
package com.example.sass.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sass.data.datasource.local.picture.PictureDao
import com.example.sass.data.datasource.local.picture.models.PictureFavoriteDao
import com.example.sass.data.datasource.local.token.models.AuthTokenDao
import com.example.sass.data.datasource.local.user.models.UserInfoDao
import com.example.sass.data.datasource.local.token.TokenDao
import com.example.sass.data.datasource.local.user.UserDao

@Database(
    entities = [AuthTokenDao::class, UserInfoDao::class, PictureFavoriteDao::class],
    version = DB_VERSION,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getTokenDao(): TokenDao
    abstract fun getPicturesDao(): PictureDao
}

private const val DB_VERSION = 1
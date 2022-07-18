package com.example.sass.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sass.data.datasource.local.picture.PictureDao
import com.example.sass.data.datasource.local.picture.models.FavoritePicDao
import com.example.sass.data.datasource.local.picture.models.PicCommonDao
import com.example.sass.data.datasource.local.user.UserDao
import com.example.sass.data.datasource.local.user.models.UserInfoDao

@Database(
    entities = [UserInfoDao::class, FavoritePicDao::class, PicCommonDao::class],
    version = DB_VERSION,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getPicturesDao(): PictureDao
}

private const val DB_VERSION = 1
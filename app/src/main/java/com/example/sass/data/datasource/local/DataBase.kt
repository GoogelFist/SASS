package com.example.sass.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sass.data.datasource.local.favoritepicture.PicturesFavoriteDao
import com.example.sass.data.datasource.local.favoritepicture.models.FavoritePictureDao
import com.example.sass.data.datasource.local.picture.PicturesDao
import com.example.sass.data.datasource.local.picture.models.PictureDao
import com.example.sass.data.datasource.local.user.UserDao
import com.example.sass.data.datasource.local.user.models.UserInfoDao

@Database(
    entities = [UserInfoDao::class, FavoritePictureDao::class, PictureDao::class],
    version = DB_VERSION,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getPicturesDao(): PicturesDao
    abstract fun getFavoritesPicturesDao(): PicturesFavoriteDao
}

private const val DB_VERSION = 1
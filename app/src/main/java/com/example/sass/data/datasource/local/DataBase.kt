package com.example.sass.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sass.data.datasource.local.picture.PicturesDao
import com.example.sass.data.datasource.local.picture.models.FavoriteDbEntity
import com.example.sass.data.datasource.local.picture.models.PictureDbEntity
import com.example.sass.data.datasource.local.user.UserDao
import com.example.sass.data.datasource.local.user.models.UserInfoDbEntity

@Database(
    entities = [UserInfoDbEntity::class, FavoriteDbEntity::class, PictureDbEntity::class],
    version = DB_VERSION,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getPicturesDao(): PicturesDao
}

private const val DB_VERSION = 1
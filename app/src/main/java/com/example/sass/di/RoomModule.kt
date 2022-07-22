package com.example.sass.di

import android.app.Application
import androidx.room.Room
import com.example.sass.data.datasource.local.DataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDataRoomDatabase(application: Application): DataBase {
        return Room.databaseBuilder(application, DataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesUserDAO(roomDataBase: DataBase) = roomDataBase.getUserDao()

    @Singleton
    @Provides
    fun providesPicturesDAO(roomDataBase: DataBase) = roomDataBase.getPicturesDao()

    companion object {
        private const val DB_NAME = "room_database"
    }
}
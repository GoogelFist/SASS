package com.example.sass.di

import android.app.Application
import androidx.room.Room
import com.example.sass.data.datasource.local.UserDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDataRoomDatabase(application: Application): UserDataBase {
        return Room.databaseBuilder(application, UserDataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesMovieDAO(userDataBase: UserDataBase) = userDataBase.getUserDao()

    companion object {
        private const val DB_NAME = "user_database"
    }
}
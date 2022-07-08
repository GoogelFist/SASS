package com.example.sass.di

import com.example.sass.data.UserRepository
import com.example.sass.data.datasourse.remote.RemoteDataSource
import com.example.sass.data.datasourse.remote.RetrofitDataSourceImpl
import com.example.sass.domain.Repository
import dagger.Binds
import dagger.Module


@Module
interface BindDataModule {

    @Binds
    fun bindRemoteDataSource(impl: RetrofitDataSourceImpl): RemoteDataSource

    @Binds
    fun bindRepository(impl: UserRepository): Repository

//    @Binds
//    fun bindLocalDataSource(impl: RoomDataSourceImpl): LocalDataSource
}
package com.example.sass.di

import com.example.sass.data.user.UserUserRepository
import com.example.sass.data.user.datasource.local.UserLocalDataSource
import com.example.sass.data.user.datasource.local.UserRoomDataSourceImpl
import com.example.sass.data.user.datasource.remote.UserRemoteDataSource
import com.example.sass.data.user.datasource.remote.UserRetrofitDataSourceImpl
import com.example.sass.domain.UserRepository
import dagger.Binds
import dagger.Module


@Module
interface BindDataModule {

    @Binds
    fun bindRemoteDataSource(impl: UserRetrofitDataSourceImpl): UserRemoteDataSource

    @Binds
    fun bindRepository(impl: UserUserRepository): UserRepository

    @Binds
    fun bindLocalDataSource(impl: UserRoomDataSourceImpl): UserLocalDataSource
}
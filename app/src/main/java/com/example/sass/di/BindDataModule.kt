package com.example.sass.di

import com.example.sass.data.user.UserRepositoryImpl
import com.example.sass.data.user.datasource.local.token.TokenLocalDataSource
import com.example.sass.data.user.datasource.local.token.TokenRoomDataSourceImpl
import com.example.sass.data.user.datasource.local.user.UserLocalDataSource
import com.example.sass.data.user.datasource.local.user.UserRoomDataSourceImpl
import com.example.sass.data.user.datasource.remote.UserRemoteDataSource
import com.example.sass.data.user.datasource.remote.UserRetrofitDataSourceImpl
import com.example.sass.domain.UserRepository
import dagger.Binds
import dagger.Module


@Module
interface BindDataModule {

    @Binds
    fun bindUserRemoteDataSource(impl: UserRetrofitDataSourceImpl): UserRemoteDataSource

    @Binds
    fun bindRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindUserLocalDataSource(impl: UserRoomDataSourceImpl): UserLocalDataSource

    @Binds
    fun bindTokenLocalDataSource(impl: TokenRoomDataSourceImpl): TokenLocalDataSource
}
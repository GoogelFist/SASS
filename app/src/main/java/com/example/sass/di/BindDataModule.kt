package com.example.sass.di

import com.example.sass.data.PicturesRepositoryImpl
import com.example.sass.data.UserRepositoryImpl
import com.example.sass.data.datasource.local.token.TokenLocalDataSource
import com.example.sass.data.datasource.local.token.TokenRoomDataSourceImpl
import com.example.sass.data.datasource.local.user.UserLocalDataSource
import com.example.sass.data.datasource.local.user.UserRoomDataSourceImpl
import com.example.sass.data.datasource.remote.picture.PicturesRemoteDataSource
import com.example.sass.data.datasource.remote.picture.PicturesRetrofitDataSourceImpl
import com.example.sass.data.datasource.remote.user.UserRemoteDataSource
import com.example.sass.data.datasource.remote.user.UserRetrofitDataSourceImpl
import com.example.sass.domain.PicturesRepository
import com.example.sass.domain.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface BindDataModule {

    @Binds
    fun bindUserRemoteDataSource(impl: UserRetrofitDataSourceImpl): UserRemoteDataSource

    @Binds
    @Singleton
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindPicturesRepository(impl: PicturesRepositoryImpl): PicturesRepository

    @Binds
    fun bindUserLocalDataSource(impl: UserRoomDataSourceImpl): UserLocalDataSource

    @Binds
    fun bindTokenLocalDataSource(impl: TokenRoomDataSourceImpl): TokenLocalDataSource

    @Binds
    fun bindPicturesRemoteDataSource(impl: PicturesRetrofitDataSourceImpl): PicturesRemoteDataSource
}
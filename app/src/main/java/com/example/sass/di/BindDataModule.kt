package com.example.sass.di

import com.example.sass.data.PicturesRepositoryImpl
import com.example.sass.data.UserRepositoryImpl
import com.example.sass.data.datasource.local.picture.PictureLocalDataSource
import com.example.sass.data.datasource.local.picture.PicturesRoomDataSourceImpl
import com.example.sass.data.datasource.local.token.TokenEncryptedSharedPrefDataSourceImpl
import com.example.sass.data.datasource.local.token.TokenLocalDataSource
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


@Module
interface BindDataModule {

    @Binds
    fun bindUserRemoteDataSource(impl: UserRetrofitDataSourceImpl): UserRemoteDataSource

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindUserLocalDataSource(impl: UserRoomDataSourceImpl): UserLocalDataSource

    @Binds
    fun bindTokenLocalDataSource(impl: TokenEncryptedSharedPrefDataSourceImpl): TokenLocalDataSource

    @Binds
    fun bindPicturesRepository(impl: PicturesRepositoryImpl): PicturesRepository

    @Binds
    fun bindPicturesRemoteDataSource(impl: PicturesRetrofitDataSourceImpl): PicturesRemoteDataSource

    @Binds
    fun bindPicturesLocalDataSource(impl: PicturesRoomDataSourceImpl): PictureLocalDataSource
}
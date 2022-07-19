package com.example.sass.di

import com.example.sass.data.datasource.remote.picture.PicturesRetrofitService
import com.example.sass.data.datasource.remote.user.UserRetrofitService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserRetrofitService(retrofit: Retrofit): UserRetrofitService {
        return retrofit.create(UserRetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun providePicturesRetrofitService(retrofit: Retrofit): PicturesRetrofitService {
        return retrofit.create(PicturesRetrofitService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://pictures.chronicker.fun/api/"
    }
}
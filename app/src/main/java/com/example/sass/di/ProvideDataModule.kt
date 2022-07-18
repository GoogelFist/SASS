package com.example.sass.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProvideDataModule {

    @Provides
    @Singleton
    fun provideEncryptedSharedPrefs(application: Application): SharedPreferences {

        val masterKeyAlias = MasterKey.Builder(application, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return EncryptedSharedPreferences.create(
            application,
            PREFS_FILE_NAME,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        private const val PREFS_FILE_NAME = "secret_shared_prefs"
    }
}
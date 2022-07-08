package com.example.sass


import android.app.Application
import android.content.Context
import com.example.sass.di.ApplicationComponent
import com.example.sass.di.DaggerApplicationComponent

class MainApplication: Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        appComponent = DaggerApplicationComponent.create()
        super.onCreate()
    }
}

val Context.component: ApplicationComponent
    get() = when (this) {
        is MainApplication -> appComponent
        else -> this.applicationContext.component
    }
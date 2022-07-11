package com.example.sass.di

import android.app.Application
import com.example.sass.presentation.screens.auth.SignInFragment
import com.example.sass.presentation.screens.splash.SplashFragment
import com.example.sass.presentation.screens.tabs.main.MainFragment
import com.example.sass.presentation.screens.tabs.profile.ProfileTabFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class, BindDataModule::class, RoomModule::class])
@Singleton
interface ApplicationComponent {

    fun inject(signInFragment: SignInFragment)

    fun inject(splashFragment: SplashFragment)

    fun inject(profileTabFragment: ProfileTabFragment)

    fun inject(mainFragment: MainFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
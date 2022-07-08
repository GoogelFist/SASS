package com.example.sass.di

import com.example.sass.presentation.screens.auth.SignInFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RetrofitModule::class, BindDataModule::class])
@Singleton
interface ApplicationComponent {

    fun inject(signInFragment: SignInFragment)

}
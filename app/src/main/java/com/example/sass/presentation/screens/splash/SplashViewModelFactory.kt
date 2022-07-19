package com.example.sass.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.usecases.IsSignedInUseCase
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(private val isSignedInUseCase: IsSignedInUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(isSignedInUseCase) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
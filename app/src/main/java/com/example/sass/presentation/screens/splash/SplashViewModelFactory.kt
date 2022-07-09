package com.example.sass.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.IsAbsentTokenUseCase
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(private val isAbsentTokenUseCase: IsAbsentTokenUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(isAbsentTokenUseCase) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
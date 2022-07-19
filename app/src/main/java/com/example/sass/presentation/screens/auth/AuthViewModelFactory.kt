package com.example.sass.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.usecases.ClearUserDataUseCase
import com.example.sass.domain.usecases.SingInUseCase
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val singInUseCase: SingInUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(singInUseCase, clearUserDataUseCase) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
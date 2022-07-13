package com.example.sass.presentation.screens.tabs.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.usecases.LoadUserInfoUseCase
import com.example.sass.domain.usecases.SingOutUseCase
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(
    private val singOutUseCase: SingOutUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(singOutUseCase, loadUserInfoUseCase) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
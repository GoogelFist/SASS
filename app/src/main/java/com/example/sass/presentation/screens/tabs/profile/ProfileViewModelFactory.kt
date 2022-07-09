package com.example.sass.presentation.screens.tabs.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.SingOutUseCase
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(private val singOutUseCase: SingOutUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(singOutUseCase) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
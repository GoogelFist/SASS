package com.example.sass.presentation.screens.tabs.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.ClearUserDataUseCase
import com.example.sass.domain.LoadUserInfoUseCase
import com.example.sass.domain.SingOutUseCase
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(
    private val singOutUseCase: SingOutUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(singOutUseCase, loadUserInfoUseCase, clearUserDataUseCase) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
package com.example.sass.presentation.screens.tabs.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.GetPicturesItemsUseCase
import com.example.sass.domain.LoadPicturesItemsUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val loadPicturesItemsUseCase: LoadPicturesItemsUseCase,
    private val getPicturesItemsUseCase: GetPicturesItemsUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(loadPicturesItemsUseCase, getPicturesItemsUseCase) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
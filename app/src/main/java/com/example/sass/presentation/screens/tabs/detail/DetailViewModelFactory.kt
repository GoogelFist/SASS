package com.example.sass.presentation.screens.tabs.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.usecases.GetPictureDetailUseCase
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(
    private val getPictureDetailUseCase: GetPictureDetailUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                getPictureDetailUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
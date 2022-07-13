package com.example.sass.presentation.screens.tabs.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.*
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val loadPicturesItemsUseCase: LoadPicturesItemsUseCase,
    private val getPicturesItemsUseCase: GetPicturesItemsUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase,
    private val addPictureItemToFavoriteUseCase: AddPictureItemToFavoriteUseCase,
    private val removePictureItemFromFavoriteUseCase: RemovePictureItemFromFavoriteUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                loadPicturesItemsUseCase,
                getPicturesItemsUseCase,
                clearUserDataUseCase,
                addPictureItemToFavoriteUseCase,
                removePictureItemFromFavoriteUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
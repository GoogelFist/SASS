package com.example.sass.presentation.screens.tabs.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.usecases.AddPictureItemToFavoriteUseCase
import com.example.sass.domain.usecases.GetPicturesItemsUseCase
import com.example.sass.domain.usecases.LoadPicturesItemsUseCase
import com.example.sass.domain.usecases.RemovePictureItemFromFavoriteUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val loadPicturesItemsUseCase: LoadPicturesItemsUseCase,
    private val getPicturesItemsUseCase: GetPicturesItemsUseCase,
    private val addPictureItemToFavoriteUseCase: AddPictureItemToFavoriteUseCase,
    private val removePictureItemFromFavoriteUseCase: RemovePictureItemFromFavoriteUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                loadPicturesItemsUseCase,
                getPicturesItemsUseCase,
                addPictureItemToFavoriteUseCase,
                removePictureItemFromFavoriteUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
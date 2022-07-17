package com.example.sass.presentation.screens.tabs.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.usecases.RemovePictureItemFromFavoriteUseCase
import com.example.sass.domain.usecases.GetFavoritesPicturesItemsUseCase
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(
    private val getFavoritesPicturesItemsUseCase: GetFavoritesPicturesItemsUseCase,
    private val removePictureItemFromFavoriteUseCase: RemovePictureItemFromFavoriteUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(
                getFavoritesPicturesItemsUseCase,
                removePictureItemFromFavoriteUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
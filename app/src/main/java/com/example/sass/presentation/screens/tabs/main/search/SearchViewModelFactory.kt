package com.example.sass.presentation.screens.tabs.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sass.domain.usecases.RemovePictureItemFromFavoriteUseCase
import com.example.sass.domain.usecases.AddPictureItemToFavoriteUseCase
import com.example.sass.domain.usecases.FindPicturesItemsUseCase
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
    private val findPicturesItemsUseCase: FindPicturesItemsUseCase,
    private val addPictureItemToFavoriteUseCase: AddPictureItemToFavoriteUseCase,
    private val removePictureItemFromFavoriteUseCase: RemovePictureItemFromFavoriteUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                findPicturesItemsUseCase,
                addPictureItemToFavoriteUseCase,
                removePictureItemFromFavoriteUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
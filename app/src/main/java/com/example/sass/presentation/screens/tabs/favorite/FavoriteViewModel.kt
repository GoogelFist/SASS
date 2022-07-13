package com.example.sass.presentation.screens.tabs.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.domain.RemovePictureItemFromFavoriteUseCase
import com.example.sass.domain.models.FavoritePicturesItem
import com.example.sass.domain.usecases.AddPictureItemToFavoriteUseCase
import com.example.sass.domain.usecases.GetFavoritesPicturesItemsUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.tabs.favorite.models.FavoriteEvent
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoritesPicturesItemsUseCase: GetFavoritesPicturesItemsUseCase,
    private val addPictureItemToFavoriteUseCase: AddPictureItemToFavoriteUseCase,
    private val removePictureItemFromFavoriteUseCase: RemovePictureItemFromFavoriteUseCase
) : ViewModel(),
    EventHandler<FavoriteEvent> {

    private var _favoritePicturesItems = MutableLiveData<List<FavoritePicturesItem>>()
    val favoritePicturesItems: LiveData<List<FavoritePicturesItem>>
        get() = _favoritePicturesItems

    init {
        loadedPictures()
    }

    override fun obtainEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.OnAddToFavorite -> addedToFavorite(event.id)
            is FavoriteEvent.OnRemoveFromFavorite -> removedFromFavorite(event.id)
            FavoriteEvent.OnUpdateUi -> loadedPictures()
        }
    }

    private fun loadedPictures() {
        viewModelScope.launch {
            updatePicturesItems()
        }
    }

    private fun addedToFavorite(id: String) {
        viewModelScope.launch {
            addPictureItemToFavoriteUseCase(id)

            updatePicturesItems()
        }
    }

    private fun removedFromFavorite(id: String) {
        viewModelScope.launch {
            removePictureItemFromFavoriteUseCase(id)

            updatePicturesItems()
        }
    }

    private suspend fun updatePicturesItems() {
        val list = getFavoritesPicturesItemsUseCase()
        _favoritePicturesItems.value = list
    }

}

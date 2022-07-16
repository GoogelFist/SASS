package com.example.sass.presentation.screens.tabs.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.domain.RemovePictureItemFromFavoriteUseCase
import com.example.sass.domain.models.FavoritePicItem
import com.example.sass.domain.usecases.GetFavoritesPicturesItemsUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.tabs.favorite.models.FavoriteEvent
import com.example.sass.presentation.screens.tabs.favorite.models.FavoriteScrollState
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoritesPicturesItemsUseCase: GetFavoritesPicturesItemsUseCase,
    private val removePictureItemFromFavoriteUseCase: RemovePictureItemFromFavoriteUseCase
) : ViewModel(), EventHandler<FavoriteEvent> {

    private var _favoritePicItems = MutableLiveData<List<FavoritePicItem>>()
    val favoritePicItems: LiveData<List<FavoritePicItem>>
        get() = _favoritePicItems

    private var _scrollState = MutableLiveData<FavoriteScrollState>()
    val scrollState: LiveData<FavoriteScrollState>
        get() = _scrollState

    override fun obtainEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.OnRemoveFromFavorite -> removedFromFavorite(event.id)
            FavoriteEvent.OnUpdateData -> updatedData()
            FavoriteEvent.OnSetDefaultScrollState -> {
                _scrollState.value = FavoriteScrollState.Default
            }
        }
    }

    private fun updatedData() {
        viewModelScope.launch {
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

        checkingScrollState(list)

        _favoritePicItems.value = list
    }

    private fun checkingScrollState(list: List<FavoritePicItem>) {
        val newListSize = list.size
        val oldListSize = _favoritePicItems.value?.size ?: 0

        when {
            newListSize > oldListSize -> {
                _scrollState.value = FavoriteScrollState.AddedFavorite
            }
            newListSize < oldListSize -> {
                _scrollState.value = FavoriteScrollState.RemovedFavorite
            }
            else -> _scrollState.value = FavoriteScrollState.Default
        }
    }
}

package com.example.sass.presentation.screens.tabs.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.domain.models.FavoritePictureItem
import com.example.sass.domain.usecases.GetFavoritesPicturesItemsUseCase
import com.example.sass.domain.usecases.RemovePictureItemFromFavoriteUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.tabs.favorite.models.FavoriteEvent
import com.example.sass.presentation.screens.tabs.favorite.models.FavoriteScrollState
import com.example.sass.presentation.screens.tabs.favorite.models.FavoriteState
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoritesPicturesItemsUseCase: GetFavoritesPicturesItemsUseCase,
    private val removePictureItemFromFavoriteUseCase: RemovePictureItemFromFavoriteUseCase
) : ViewModel(), EventHandler<FavoriteEvent> {

    private var _favoritePictureItems = MutableLiveData<List<FavoritePictureItem>>()
    val favoritePictureItems: LiveData<List<FavoritePictureItem>>
        get() = _favoritePictureItems

    private var _scrollState = MutableLiveData<FavoriteScrollState>()
    val scrollState: LiveData<FavoriteScrollState>
        get() = _scrollState

    private var _favoriteState = MutableLiveData<FavoriteState>()
    val favoriteState: LiveData<FavoriteState>
        get() = _favoriteState

    override fun obtainEvent(event: FavoriteEvent) {
        when (event) {
            is FavoriteEvent.OnRemoveFromFavorite -> removedFromFavorite(event.id)
            FavoriteEvent.OnUpdateData -> updatedData()
            FavoriteEvent.OnSetDefaultScrollState -> setDefaultScrollState()
        }
    }

    private fun updatedData() {
        viewModelScope.launch {
            updatePicturesItems()
        }
    }

    private fun setDefaultScrollState() {
        _scrollState.value = FavoriteScrollState.Default
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

        checkEmptyList(list)

        _favoritePictureItems.value = list
    }

    private fun checkingScrollState(list: List<FavoritePictureItem>) {
        val newListSize = list.size
        val oldListSize = _favoritePictureItems.value?.size ?: 0

        when {
            newListSize > oldListSize -> {
                _scrollState.value = FavoriteScrollState.AddedFavorite
            }
            newListSize < oldListSize -> {
                _scrollState.value = FavoriteScrollState.RemovedFavorite
            }
            else -> setDefaultScrollState()
        }
    }

    private fun checkEmptyList(list: List<FavoritePictureItem>) {
        if (list.isEmpty()) {
            _favoriteState.value = FavoriteState.Empty
        } else {
            _favoriteState.value = FavoriteState.Default
        }
    }
}

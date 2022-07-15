package com.example.sass.presentation.screens.tabs.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.domain.RemovePictureItemFromFavoriteUseCase
import com.example.sass.domain.models.PicturesItem
import com.example.sass.domain.usecases.AddPictureItemToFavoriteUseCase
import com.example.sass.domain.usecases.SearchPicturesItemsUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.tabs.main.search.models.SearchEvent
import com.example.sass.presentation.screens.tabs.main.search.models.SearchState
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchPicturesItemsUseCase: SearchPicturesItemsUseCase,
    private val addPictureItemToFavoriteUseCase: AddPictureItemToFavoriteUseCase,
    private val removePictureItemFromFavoriteUseCase: RemovePictureItemFromFavoriteUseCase
) : ViewModel(), EventHandler<SearchEvent> {

    private var searchText = ""

    private var _picturesItems = MutableLiveData<List<PicturesItem>>()
    val picturesItems: LiveData<List<PicturesItem>>
        get() = _picturesItems

    private var _searchState = MutableLiveData<SearchState>(SearchState.Default)
    val searchState: LiveData<SearchState>
        get() = _searchState

    override fun obtainEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnAddToFavorite -> addedToFavorite(event.id)
            is SearchEvent.OnRemoveFromFavorite -> removedFromFavorite(event.id)
            is SearchEvent.OnSearchPictures -> searchData(event.text)
            SearchEvent.OnSetDefaultState -> setDefaultState()
        }
    }


    private fun addedToFavorite(id: String) {
        viewModelScope.launch {
            addPictureItemToFavoriteUseCase(id)

            updatePicturesItems(searchText)
        }
    }

    private fun removedFromFavorite(id: String) {
        viewModelScope.launch {
            removePictureItemFromFavoriteUseCase(id)

            updatePicturesItems(searchText)
        }
    }

    private suspend fun updatePicturesItems(text: String) {
        if (text.isNotBlank()) {
            val list = searchPicturesItemsUseCase(text)
            checkEmptyList(list)
            _picturesItems.value = list
        } else {
            _searchState.value = SearchState.Default
        }
    }


    private fun searchData(text: String) {
        searchText = text

        viewModelScope.launch {
            _searchState.value = SearchState.Default
            updatePicturesItems(text)
        }
    }

    private fun checkEmptyList(list: List<PicturesItem>) {
        if (list.isEmpty()) {
            _searchState.value = SearchState.NotFound
        } else {
            _searchState.value = SearchState.Found
        }
    }

    private fun setDefaultState() {
        _searchState.value = SearchState.Default
    }
}

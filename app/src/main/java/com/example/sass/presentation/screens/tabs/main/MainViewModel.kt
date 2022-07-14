package com.example.sass.presentation.screens.tabs.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.data.IncorrectTokenException
import com.example.sass.domain.*
import com.example.sass.domain.models.PicturesItem
import com.example.sass.domain.usecases.AddPictureItemToFavoriteUseCase
import com.example.sass.domain.usecases.GetPicturesItemsUseCase
import com.example.sass.domain.usecases.LoadPicturesItemsUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.tabs.main.models.MainEvent
import com.example.sass.presentation.screens.tabs.main.models.MainState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    private val loadPicturesItemsUseCase: LoadPicturesItemsUseCase,
    private val getPicturesItemsUseCase: GetPicturesItemsUseCase,
    private val addPictureItemToFavoriteUseCase: AddPictureItemToFavoriteUseCase,
    private val removePictureItemFromFavoriteUseCase: RemovePictureItemFromFavoriteUseCase
) : ViewModel(), EventHandler<MainEvent> {

    private var _picturesItems = MutableLiveData<List<PicturesItem>>()
    val picturesItems: LiveData<List<PicturesItem>>
        get() = _picturesItems

    private var _mainState = MutableLiveData<MainState>()
    val mainState: LiveData<MainState>
        get() = _mainState

    override fun obtainEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnAddToFavorite -> addedToFavorite(event.id)
            is MainEvent.OnRemoveFromFavorite -> removedFromFavorite(event.id)
            MainEvent.OnLoadPictures -> loadedPictures()
            MainEvent.OnRefresh -> refreshedPictures()
            MainEvent.OnUpdateData -> updatedData()
        }
    }

    init {
        loadedPictures()
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
        val list = getPicturesItemsUseCase()
        _picturesItems.value = list
    }

    private fun loadedPictures(): Job {
        return viewModelScope.launch {
            try {
                _mainState.value = MainState.Loading

                loadPicturesItemsUseCase()

                val list = getPicturesItemsUseCase()
                checkEmptyList(list)

                _picturesItems.value = list
            } catch (error: Throwable) {
                when (error) {
                    is IncorrectTokenException -> {
                        _mainState.value = MainState.IncorrectToken
                    }
                    else -> {
                        _mainState.value = MainState.ErrorLoaded
                    }
                }
            }
        }
    }

    private fun refreshedPictures() {
        viewModelScope.launch {
            try {
                _mainState.value = MainState.Refreshing

                loadPicturesItemsUseCase()

                val list = getPicturesItemsUseCase()

                checkEmptyList(list)

                _picturesItems.value = list
            } catch (error: Throwable) {
                when (error) {
                    is IncorrectTokenException -> {
                        _mainState.value = MainState.IncorrectToken
                    }
                    else -> {
                        _mainState.value = MainState.RefreshedError
                    }
                }
            }
        }
    }

    private fun updatedData() {
        viewModelScope.launch {
            updatePicturesItems()
        }
    }

    private fun checkEmptyList(list: List<PicturesItem>) {
        if (list.isEmpty()) {
            _mainState.value = MainState.EmptyList
        } else {
            _mainState.value = MainState.Loaded
        }
    }
}

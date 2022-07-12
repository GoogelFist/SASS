package com.example.sass.presentation.screens.tabs.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.data.IncorrectTokenException
import com.example.sass.domain.LoadPicturesItemsUseCase
import com.example.sass.domain.models.PicturesItem
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.tabs.main.models.MainEvent
import com.example.sass.presentation.screens.tabs.main.models.MainState
import kotlinx.coroutines.launch

class MainViewModel(private val loadPicturesItemsUseCase: LoadPicturesItemsUseCase) : ViewModel(),
    EventHandler<MainEvent> {

    private var _picturesItemList = MutableLiveData<List<PicturesItem>>()
    val picturesItemList: LiveData<List<PicturesItem>>
        get() = _picturesItemList

    private var _mainState = MutableLiveData<MainState>()
    val mainState: LiveData<MainState>
        get() = _mainState

    override fun obtainEvent(event: MainEvent) {
        when (event) {
            MainEvent.OnInitFragment -> setInitState()
            is MainEvent.OnAddToFavorite -> addedToFavorite(event.id)
            is MainEvent.OnRemoveFromFavorite -> removedFromFavorite(event.id)
            MainEvent.OnLoadPictures -> loadedPictures()
            MainEvent.OnRefresh -> refreshedPictures()
        }
    }

    init {
        loadedPictures()
    }

    private fun setInitState() {
        _mainState.value = MainState.Loaded
    }

    private fun addedToFavorite(id: String) {
        TODO("Not yet implemented")
    }

    private fun removedFromFavorite(id: String) {
        TODO("Not yet implemented")
    }


    private fun loadedPictures() {
        viewModelScope.launch {
            try {
                _mainState.value = MainState.Loading
                val list = loadPicturesItemsUseCase()

                if (list.isEmpty()) {
                    _mainState.value = MainState.EmptyList
                } else {
                    _mainState.value = MainState.Loaded
                }
                _picturesItemList.value = list
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

                val list = loadPicturesItemsUseCase()

                if (list.isEmpty()) {
                    _mainState.value = MainState.EmptyList
                } else {
                    _mainState.value = MainState.Loaded
                }
                _picturesItemList.value = list
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
}

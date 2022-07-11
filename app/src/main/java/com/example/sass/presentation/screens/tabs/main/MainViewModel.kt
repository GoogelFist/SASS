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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val loadPicturesItemsUseCase: LoadPicturesItemsUseCase) : ViewModel(),
    EventHandler<MainEvent> {

    private var _picturesItemList = MutableLiveData<List<PicturesItem>>()
    val picturesItemList: LiveData<List<PicturesItem>>
        get() = _picturesItemList

    private var _mainState = MutableLiveData<MainState>()
    val mainState: LiveData<MainState>
        get() = _mainState

    init {
        getPictures()
    }

    override fun obtainEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnAddToFavorite -> addedToFavorite(event.id)
            is MainEvent.OnRemoveFromFavorite -> removedFromFavorite(event.id)
            MainEvent.OnRefresh -> refreshed()
        }
    }

    private fun addedToFavorite(id: String) {
        TODO("Not yet implemented")
    }

    private fun removedFromFavorite(id: String) {
        TODO("Not yet implemented")
    }

    private fun refreshed() {
        TODO("Not yet implemented")
    }

    private fun getPictures() {
        viewModelScope.launch {
            try {
//                _mainState.value = MainState.Default
                _mainState.value = MainState.Loading

                delay(2000)
                throw RuntimeException()
//                val list = loadPicturesItemsUseCase()
//                _picturesItemList.value = list
//
//                _mainState.value = MainState.Loaded
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
}

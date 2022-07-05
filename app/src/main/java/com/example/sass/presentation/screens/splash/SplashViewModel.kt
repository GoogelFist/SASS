package com.example.sass.presentation.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private var _launchMainScreenEvent = MutableLiveData<Boolean>()
    val launchMainScreenEvent: LiveData<Boolean>
        get() = _launchMainScreenEvent

    init {
        viewModelScope.launch {
            _launchMainScreenEvent.postValue(false)
        }
    }
}
package com.example.sass.presentation.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.domain.IsAbsentTokenUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.splash.models.SplashEvent
import com.example.sass.presentation.screens.splash.models.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val isAbsentTokenUseCase: IsAbsentTokenUseCase) : ViewModel(),
    EventHandler<SplashEvent> {

    private var _splashState = MutableLiveData<SplashState>()
    val splashState: LiveData<SplashState>
        get() = _splashState

    init {
        lunchMainScreen()
    }

    override fun obtainEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.LaunchMainScreen -> lunchMainScreen()
        }
    }

    private fun lunchMainScreen() {
        viewModelScope.launch {
            val isAbsentToken = isAbsentTokenUseCase()
            val isSignIn = !isAbsentToken
            delay(LAUNCH_DELAY)
            _splashState.postValue(SplashState.LaunchingMainScreen(isSignIn))
        }
    }

    companion object {
        private const val LAUNCH_DELAY = 500L
    }
}
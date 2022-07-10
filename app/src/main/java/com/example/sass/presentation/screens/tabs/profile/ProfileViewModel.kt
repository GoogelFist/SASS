package com.example.sass.presentation.screens.tabs.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.data.user.IncorrectTokenException
import com.example.sass.domain.SingOutUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.tabs.profile.models.ProfileEvent
import com.example.sass.presentation.screens.tabs.profile.models.ProfileState
import kotlinx.coroutines.launch

class ProfileViewModel(private val singOutUseCase: SingOutUseCase) : ViewModel(),
    EventHandler<ProfileEvent> {

    private var _profileState = MutableLiveData<ProfileState>()
    val profileState: LiveData<ProfileState>
        get() = _profileState

    override fun obtainEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnDefaultState -> setDefaultState()
            ProfileEvent.OnSignOutEvent -> signOut()
        }
    }

    // TODO: will need to load user info to live data and will to observe it

    private fun setDefaultState() {
        _profileState.value = ProfileState.Default
    }

    private fun signOut() {
        viewModelScope.launch {
            try {
                setDefaultState()
                _profileState.value = ProfileState.SigningOutState
                singOutUseCase()
                _profileState.value = ProfileState.SignedOutState

            } catch (error: Throwable) {
                when (error) {
                    is IncorrectTokenException -> {
                        _profileState.value = ProfileState.IncorrectTokenState
                    }
                    else -> {
                        _profileState.value = ProfileState.SingOutErrorState
                    }
                }
            }
        }
    }
}

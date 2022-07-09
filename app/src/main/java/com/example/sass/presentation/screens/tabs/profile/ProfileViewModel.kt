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
            ProfileEvent.OnSignOutEvent -> signOut()
        }
    }

    // TODO: will need to load user info to live data and will to observe it

    private fun signOut() {
        viewModelScope.launch {
            try {
                _profileState.postValue(ProfileState.SigningOutState)
                singOutUseCase()
                _profileState.postValue(ProfileState.SignedOutState)

            } catch (error: Throwable) {
                when (error) {
                    is IncorrectTokenException -> {
                        _profileState.postValue(ProfileState.IncorrectTokenState)
                    }
                    else -> {
                        _profileState.postValue(ProfileState.SingOutErrorState)
                    }
                }
            }
        }
    }
}
